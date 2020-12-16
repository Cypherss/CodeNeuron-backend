package com.example.codeneuron.ServiceImpl.CalculateService.Dynamic;

import com.example.codeneuron.Dao.DomainMapper;
import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.Dao.ProjectMapper;
import com.example.codeneuron.PO.*;
import com.example.codeneuron.Service.CalculateService.Dynamic.DomainCal;
import com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DomainCalImpl implements DomainCal {
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    EdgeMapper edgeMapper;
    @Autowired
    InitGraph initGraph;
    @Autowired
    GraphCal graphCal;
    @Autowired
    DomainMapper domainMapper;
    @Autowired
    ProjectMapper projectMapper;
    //阈值
//    private double threshold;
//    private Set<Set<Node>> connectedDomain;
//
//    public DomainCalImpl(){
//        this.threshold=0;
//        connectedDomain=new HashSet<Set<Node>>();
//    }
//
//    public double getThreshold() {
//        return threshold;
//    }
//
//
//
//    public Set getConnectedDomain(){ return connectedDomain; }
//
//    @Override
//    public ResponseVO setThreshold(double threshold) {
//        this.threshold = threshold;
//        System.out.println("set threshold "+threshold+" successfully");
//        connectedDomain.clear();
//        return ResponseVO.buildSuccess();
//    }

    /**
     * 连通域个数计算
     * @return
     */
    @Override
    public ResponseVO ConnectedDomainCount(double threshold, int projectId){
        List<Node> nodes = nodeMapper.selectNodeByProjectId(projectId);
        List<Edge> edges = edgeMapper.selectEdgeByProjectId(projectId);
        HashMap<String, LinkedList<GraphNode>> graph = initGraph.InitAdjacencyTable(nodes, edges);
        HashMap<String, LinkedList<GraphNode>> inverseGraph = initGraph.InitInverseAdjacencyTable(nodes, edges);
        graphCal.ClosenessCalculate(1,graph,inverseGraph);

        int numOfDomains=0;
        Set<Set<Node>> connectedDomain=new HashSet<Set<Node>>();
        double tempThreshold=threshold;
//        threshold=0;
        for(HashMap.Entry<String, LinkedList<GraphNode>>entry: graph.entrySet()){
            String key=entry.getKey();
            LinkedList<GraphNode> GraphNodes=entry.getValue();
            if(GraphNodes.getFirst().getNode().getVisited()!=true) {
                Set tempSet = new HashSet();
                connectedDomain.add(SetCalculate(tempSet, key,graph,inverseGraph,tempThreshold,nodes));
                //将visited置true
                Iterator<Node> iterator=tempSet.iterator();
                while(iterator.hasNext()){
                    Node tem=iterator.next();
                    tem.setVisited(true);
                    //System.out.print(tem.getName()+",");
                }
            }
            //System.out.println();
        }
        //还原
        for(Node node:nodes){
            node.setVisited(false);
        }
        threshold=tempThreshold;

        numOfDomains=connectedDomain.size();
        return ResponseVO.buildSuccess(numOfDomains);
    }

    @Override
    public ResponseVO getDomainCountByThresholdAndProjectId(double threshold, int projectId){
        int result = domainMapper.getDomainByProjectIdAndCloseness(projectId,threshold).size();
        if(result==0){
            return ResponseVO.buildFailure("No history domain of threshold "+(threshold+""));
        }else{
            return ResponseVO.buildSuccess(result);
        }
    }
    /**
     * 获取连通域，去掉了单点集
     * @return
     */
    @Override
    public ResponseVO TopologyCalculate(double threshold, int projectId){
        List<Node> nodes = nodeMapper.selectNodeByProjectId(projectId);
        List<Edge> edges = edgeMapper.selectEdgeByProjectId(projectId);
        HashMap<String, LinkedList<GraphNode>> graph = initGraph.InitAdjacencyTable(nodes, edges);
        HashMap<String, LinkedList<GraphNode>> inverseGraph = initGraph.InitInverseAdjacencyTable(nodes, edges);
        graphCal.ClosenessCalculate(projectId,graph,inverseGraph);

        Set<Set<Node>> connectedDomain=new HashSet<Set<Node>>();
        for(HashMap.Entry<String,LinkedList<GraphNode>>entry: graph.entrySet()){
            String key=entry.getKey();
            LinkedList<GraphNode> GraphNodes=entry.getValue();
            if(GraphNodes.getFirst().getNode().getVisited()!=true) {
                Set tempSet = new HashSet();
                connectedDomain.add(SetCalculate(tempSet, key, graph, inverseGraph, threshold,nodes));
                //将visited置true
                Iterator<Node> iterator=tempSet.iterator();
                int numTempSet=0;
                while(iterator.hasNext()){
                    numTempSet=numTempSet+1;
                    Node tempi=iterator.next();
                    tempi.setVisited(true);
                    //System.out.print(tempi.getName()+",");
                }
                if(numTempSet<2){
                    connectedDomain.remove(tempSet);
                }
            }
            //System.out.println();
        }
        //还原
        for(Node node:nodes){
            node.setVisited(false);
        }
        //排序
        List<Set<Node>> orderedConnectedDomain=new ArrayList<>(connectedDomain);
        Collections.sort(orderedConnectedDomain, new Comparator<Set<Node>>() {
            @Override
            public int compare(Set<Node> o1, Set<Node> o2) {
                int diff=o1.size()-o2.size();
                if(diff>0){
                    return -1;
                }else if(diff<0){
                    return 1;
                }
                return 0;
            }
        });
        /*
        List<Set<Node>> orderedConnectedDomain=new ArrayList<>();
        int max=0;
        for(Set set:connectedDomain){
            max=max>set.size()?max:set.size();
        }
        for(int i=max;i>1;i--){
            for(Set set:connectedDomain){
                if (set.size()==i){
                    orderedConnectedDomain.add(set);
                }
            }
        }*/
        return ResponseVO.buildSuccess(orderedConnectedDomain);
    }

    public Set SetCalculate(Set tempSet,String key,HashMap<String, LinkedList<GraphNode>> graph,HashMap<String, LinkedList<GraphNode>> inverseGraph,double threshold,List<Node> nodes){
        LinkedList<GraphNode> tempGraphNodesOne=graph.get(key);
        LinkedList<GraphNode> tempGraphNodesTwo=inverseGraph.get(key);
        if(tempSet.contains(Project.getNodeByName(key,nodes))){
            return tempSet;
        }
        tempSet.add(tempGraphNodesOne.getFirst().getNode());
        if(tempGraphNodesOne.size()==1&&tempGraphNodesTwo.size()==1){
            return tempSet;
        }
        for(GraphNode tempGraphNode:tempGraphNodesOne){
            //if((!tempGraphNode.getNode().equals(tempGraphNodesOne.getFirst()))&& tempGraphNode.getCloseness()>=threshold) {
            if((!tempGraphNode.equals(tempGraphNodesOne.getFirst())) && tempGraphNode.getCloseness()>=threshold) {
                key = tempGraphNode.getNode().getName();
                SetCalculate(tempSet, key,graph,inverseGraph,threshold,nodes);
            }
        }

        for(GraphNode tempGraphNode:tempGraphNodesTwo){
            //if((!tempGraphNode.getNode().equals(tempGraphNodesTwo.getFirst()))&& tempGraphNode.getCloseness()>=threshold) {
            if((!tempGraphNode.equals(tempGraphNodesTwo.getFirst())) && tempGraphNode.getCloseness()>=threshold) {
                key = tempGraphNode.getNode().getName();
                SetCalculate(tempSet, key,graph,inverseGraph,threshold,nodes);
            }
        }
        return tempSet;
    }

    @Override
    public ResponseVO setThreshold(double threshold, int projectId){
        List<Set<Node>> connectedDomain = (List<Set<Node>>)TopologyCalculate(threshold,projectId).getContent();
        projectMapper.updateThreshold(projectId,threshold);
        if(domainMapper.getDomainByProjectIdAndCloseness(projectId,threshold).size()!=0){
            return ResponseVO.buildSuccess("Threshold of project "+(projectId+"")+" successfully set "+(threshold+"")+"\nUse history domain.");
        }//如果domain表中有这个记录，说明是历史domain，不用后续操作
        for(Set<Node> oneDomain: connectedDomain) {
            Domain tmp = new Domain();
            tmp.setClosenessThreshold(threshold);
            tmp.setProjectId(projectId);
            domainMapper.insertDomain(tmp);
            List<Node> nodeForDomain = new ArrayList<>();
            for(Node oneNode:oneDomain){
                nodeForDomain.add(oneNode);
            }
            nodeMapper.insertNodeForDomain(nodeForDomain, tmp.getId());
        }
        return ResponseVO.buildSuccess("Threshold of project "+(projectId+"")+" successfully set "+(threshold+""));
    }

    //只能得到当前threshold下的domain
    @Override
    public ResponseVO getDomains(int projectId){
        double closenessThreshold = projectMapper.findProjectById(projectId).getClosenessThreshold();
        List<Domain> domains = domainMapper.getDomainByProjectIdAndCloseness(projectId,closenessThreshold);
        HashMap<Integer, List<Node>> domainsWithNodes = new HashMap<>();
        for(Domain oneDomain: domains){
            List<Node> nodes = nodeMapper.selectNodeByDomain(oneDomain.getId());
            domainsWithNodes.put(oneDomain.getId(),nodes);
        }
        return ResponseVO.buildSuccess(domainsWithNodes);
    }
}
