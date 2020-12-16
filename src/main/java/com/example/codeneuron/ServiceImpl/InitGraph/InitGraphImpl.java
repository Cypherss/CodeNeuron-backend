package com.example.codeneuron.ServiceImpl.InitGraph;

import com.example.codeneuron.Dao.DomainMapper;
import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.Dao.ProjectMapper;
import com.example.codeneuron.PO.*;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import com.example.codeneuron.VO.DomainVO;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class InitGraphImpl implements InitGraph {

//    NodeMapper nodeMapper;
//    EdgeMapper edgeMapper;
//    ProjectMapper projectMapper;
//    DomainMapper domainMapper;

    LinkedList<GraphNode> temp,tempI,val;
//    @Autowired
//    public InitGraphImpl(NodeMapper nodeMapper,EdgeMapper edgeMapper,ProjectMapper projectMapper,DomainMapper domainMapper){
//        this.nodeMapper=nodeMapper;
//        this.edgeMapper=edgeMapper;
//        this.projectMapper=projectMapper;
//        this.domainMapper=domainMapper;
//    }

    /**
     * 邻接表和逆邻接表生成
     * @return
     */
    @Override
    public HashMap<String, LinkedList<GraphNode>> InitAdjacencyTable(List<Node> nodes, List<Edge> edges){
        HashMap<String, LinkedList<GraphNode>> graph = new HashMap<>();
        for(Node node: nodes){
            if(!graph.containsKey(node.getName())){
                val=new LinkedList<>();
                val.add(new GraphNode(node));
                graph.put(node.getName(),val);
            }
        }
        Node callee;
        for(Edge edge: edges){
            temp=graph.get(edge.getCallerName());
            callee=Project.getNodeByName(edge.getCalleeName(),nodes); //nodeMapper.selectNodeByName(edge.getCalleeName());
            temp.add(new GraphNode(callee));
        }
        return graph;
    }

//    public ResponseVO getInit(int projectId){
//        Project project=this.projectMapper.findProjectById(projectId);
//        double threshold=project.getClosenessThreshold();
//        Domain historyDomain=this.domainMapper.getDomainByProjectIdAndCloseness(projectId,threshold);
//        return ResponseVO.buildSuccess(historyDomain);
//    }

    @Override
    public HashMap<String, LinkedList<GraphNode>> InitInverseAdjacencyTable(List<Node> nodes, List<Edge> edges){
        Node caller;
        HashMap<String, LinkedList<GraphNode>> inverseGraph = new HashMap<>();
        for(Node node:nodes){
            if(!inverseGraph.containsKey(node.getName())){
                val=new LinkedList<>();
                val.add(new GraphNode(node));
                inverseGraph.put(node.getName(),val);
            }
        }
        for(Edge edge:edges){
            temp=inverseGraph.get(edge.getCalleeName());
            caller=Project.getNodeByName(edge.getCallerName(),nodes); //nodeMapper.selectNodeByName(edge.getCallerName());
            temp.add(new GraphNode(caller));
        }
        return inverseGraph;
    }
}
