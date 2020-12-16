package com.example.codeneuron.ServiceImpl.CalculateService.Dynamic;

import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.Dao.RequirementMapper;
import com.example.codeneuron.Dao.Requirement_traceMapper;
import com.example.codeneuron.PO.*;
import com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import com.example.codeneuron.VO.RequirementForm;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphCalImpl implements GraphCal {

    NodeMapper nodeMapper;
    EdgeMapper edgeMapper;
    InitGraph initGraph;
    RequirementMapper requirementMapper;
    Requirement_traceMapper requirement_traceMapper;
    @Autowired
    public GraphCalImpl(NodeMapper nodeMapper,EdgeMapper edgeMapper,InitGraph initGraph,RequirementMapper requirementMapper,Requirement_traceMapper requirement_traceMapper){
        this.nodeMapper=nodeMapper;
        this.edgeMapper=edgeMapper;
        this.initGraph=initGraph;
        this.requirementMapper=requirementMapper;
        this.requirement_traceMapper=requirement_traceMapper;
    }
    /**
     * 依赖紧密度计算
     * @return
     */
    @Override
    public ResponseVO ClosenessCalculate(int projectId,HashMap<String, LinkedList<GraphNode>> graph, HashMap<String, LinkedList<GraphNode>> inverseGraph){
        for(HashMap.Entry<String,LinkedList<GraphNode>> entry: graph.entrySet()){
            String key=entry.getKey();
            LinkedList<GraphNode> GraphNodes=entry.getValue();
            double outdegree = graph.get(key).size() - 1;
            for(GraphNode graphNode:GraphNodes){
                //if(!graphNode.getNode().equals(GraphNodes.getFirst())) {
                if(!graphNode.equals(GraphNodes.getFirst())) {
                    double  indegree = inverseGraph.get(graphNode.getNode().getName()).size() - 1;
                    double edgeThreshold = 2.0 / (indegree + outdegree);
                    graphNode.setCloseness(edgeThreshold);
                }
            }
        }
        for(HashMap.Entry<String,LinkedList<GraphNode>> entryInverse: inverseGraph.entrySet()){
            String key=entryInverse.getKey();
            LinkedList<GraphNode> GraphNodes=entryInverse.getValue();
            double indegree = inverseGraph.get(key).size() - 1;
            for(GraphNode graphNode:GraphNodes){
                if(!graphNode.equals(GraphNodes.getFirst())) {
                    double  outdegree = graph.get(graphNode.getNode().getName()).size() - 1;
                    double edgeThreshold = 2.0 / (indegree + outdegree);
                    graphNode.setCloseness(edgeThreshold);
                }
            }
        }
        List<Edge> edges = edgeMapper.selectEdgeByProjectId(projectId);
        for(int i = 0;i<edges.size();i++){
            Edge targetEdge = edges.get(i);
            String calleeName = targetEdge.getCalleeName();
            String callerName = targetEdge.getCallerName();
            LinkedList<GraphNode> tmp = graph.get(callerName);
            for(GraphNode tmpNode: tmp){
                if(tmpNode.getNode().getName().equals(calleeName)){
                    targetEdge.setCloseness(tmpNode.getCloseness());
                }
            }
        }
//        edgeMapper.updateEdgeCloseness(edges);似乎还是不要在这里插入数据库比较好，但可以通过返回值拿到计算完紧密度的edges，再在方法中插入
        return ResponseVO.buildSuccess(edges);
    }

    /**
     * 顶点个数计算
     * @return
     */
    @Override
    public ResponseVO NodesCount(int projectId){
        int numOfNodes=  nodeMapper.selectNodeByProjectId(projectId).size();
        return ResponseVO.buildSuccess(numOfNodes);
    }

    /**
     * 边个数计算
     * @return
     */
    @Override
    public ResponseVO EdgesCount(int projectId){
        int numOfEdges=edgeMapper.selectEdgeByProjectId(projectId).size();
        return ResponseVO.buildSuccess(numOfEdges);
    }

    @Override
    public ResponseVO allNodes(int projectId){
        List<Node> nodes;
        try{
            nodes=nodeMapper.selectNodeByProjectId(projectId);
            return ResponseVO.buildSuccess(nodes);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("error");
        }
    }

    @Override
    public ResponseVO allEdges(int projectId){
        List<Edge> edges;
        try{
            edges=edgeMapper.selectEdgeByProjectId(projectId);
            return ResponseVO.buildSuccess(edges);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("error");
        }
    }

    /**
     * 执行需求追踪计算
     * @param projectId
     * @return
     */
    @Override
    public ResponseVO requirementTrace(int projectId){
        double threshold=0.4;
        try {
            List<Edge> edges=edgeMapper.selectEdgeByProjectId(projectId);
            List<Node> nodes=nodeMapper.selectNodeByProjectId(projectId);
            for(int i=0;i<2;i++){
                List<Requirement> requirements=requirementMapper.selectRequirementsByProjectId(projectId);
                for(Node n:nodes){
                    List<String> neighbors=countNeighbors(n.getName(),edges);
                    double size=neighbors.size()+0.0;
                    for(Requirement r:requirements){
                        int tracingNeighbors=countTracingNeighbors(r.getId(),projectId,neighbors,nodes);
                        if((tracingNeighbors/size>=threshold)){
                            //System.out.println(n.getName()+" R"+(r.getId()+1));
                            addTrace(r.getId(),projectId,n.getId());
                        }
                    }
                }
            }
            Map<Integer,List<Integer>> ans=new HashMap<>();
            List<Requirement> requirements=requirementMapper.selectRequirementsByProjectId(projectId);
            for(Requirement requirement:requirements){
                List<Requirement_trace> temp=requirement_traceMapper.selectRequirementsByProjectIdAndRequirementId(projectId,requirement.getId());
                List<Integer> nodeIdList=new ArrayList<>();
                for(Requirement_trace requirement_trace:temp){
                    nodeIdList.add(requirement_trace.getNodeId());
                }
                ans.put(requirement.getId(),nodeIdList);
            }
            return ResponseVO.buildSuccess(ans);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("error");
        }
    }

    @Override
    public ResponseVO addRequirement(RequirementForm requirementForm){
        try {
            Requirement requirement;
            requirement=requirementMapper.selectRequirementByNameAndProjectId(requirementForm.getName(),requirementForm.getProjectId());
            if(requirement==null){
                requirement=new Requirement();
                requirement.setName(requirementForm.getName());
                requirement.setDescription(requirementForm.getDescription());
                requirement.setProjectId(requirementForm.getProjectId());
                requirementMapper.createRequirement(requirement);
            }
            Requirement_trace requirement_trace=new Requirement_trace();
            requirement_trace.setProjectId(requirementForm.getProjectId());
            requirement_trace.setNodeId(requirementForm.getNodeId());
            requirement=requirementMapper.selectRequirementByNameAndProjectId(requirementForm.getName(),requirementForm.getProjectId());
            requirement_trace.setRequirementId(requirement.getId());
            requirement_traceMapper.createRequirementTrace(requirement_trace);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("error");
        }
    }

    @Override
    public ResponseVO deleteRequirement(int requirementId,int nodeId,int projectId){
        try{
            requirement_traceMapper.deleteRequirementTrace(requirementId,nodeId,projectId);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("error");
        }
    }

    List<String> countNeighbors(String name,List<Edge> edges){
        List<String> neighbors=new ArrayList<>();
        for(Edge edge:edges){
            if(edge.getCallerName().equals(name)){
                neighbors.add(edge.getCalleeName());
            }
            else if(edge.getCalleeName().equals(name)){
                neighbors.add(edge.getCallerName());
            }
        }
        return neighbors;
    }

    int countTracingNeighbors(int requirementId,int projectId,List<String> neighbors,List<Node> nodes){
        int count=0;
        for(String node:neighbors){
            int nodeId=getNodeId(nodes,node);
            List<Requirement_trace> requirement_traces=requirement_traceMapper.selectRequirementsByProjectIdAndNodeId(projectId,nodeId);
            for(Requirement_trace requirement_trace:requirement_traces){
                if(requirement_trace.getRequirementId()==requirementId){
                    count++;
                }
            }
        }
        return count;
    }

    void addTrace(int requirmenetId,int projectId,int nodeId){
        List<Requirement_trace> requirement_traces=requirement_traceMapper.selectRequirementsByProjectIdAndNodeId(projectId,nodeId);
        int flag=0;
        for(Requirement_trace requirement_trace:requirement_traces){
            if(requirement_trace.getRequirementId()==requirmenetId){
                flag=1;
                break;
            }
        }
        if(flag==0){
            System.out.println(nodeId+" R"+(requirmenetId+1));
            Requirement_trace requirement_trace=new Requirement_trace(requirmenetId,projectId,nodeId);
            requirement_traceMapper.createRequirementTrace(requirement_trace);
        }
    }

    int getNodeId(List<Node> nodes,String name){
        int id=0;
        for(Node node:nodes){
            if(node.getName().equals(name)){
                id=node.getId();
            }
        }
        return id;
    }


}
