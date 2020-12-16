package com.example.codeneuron.Service.CalculateService.Dynamic;

import com.example.codeneuron.Dao.DomainMapper;
import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.PO.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DomainCalTest {
    List<Node> nodes;
    List<Edge> edges;
    HashMap<String, LinkedList<GraphNode>> graph;
    HashMap<String, LinkedList<GraphNode>> inverseGraph;
    @Autowired
    DomainCal domainCal;
    @Autowired
    GraphCal graphCal;
    @Autowired
    EdgeMapper edgeMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    DomainMapper domainMapper;
    @Before
    public void constructGraph(){

        Node node;
        Edge edge;
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        graph = new HashMap<>();
        inverseGraph = new HashMap<>();
        nodeMapper.deleteNodeForProject(1);
        edgeMapper.deleteEdgeForProject(1);
        List<Domain> domains = domainMapper.getDomainByProjectId(1);
        domainMapper.deleteDomainByProjectId(1);
        if(domains.size()!=0) {
            nodeMapper.deleteNodeForDomains(domains);
        }
        for(int i=1;i<21;i++){
            node=new Node(i,i+"",1);
            nodes.add(node);
        }
//        edge=new Edge(0,"0","2","M",1);  edge的id不能有0，会报错，应该是因为主键的原因
//        edges.add(edge);
        edge=new Edge(1,"1","2","M",1);
        edges.add(edge);
        edge=new Edge(2,"1","3","M",1);
        edges.add(edge);
        edge=new Edge(3,"2","4","M",1);
        edges.add(edge);
        edge=new Edge(4,"3","4","M",1);
        edges.add(edge);
        edge=new Edge(5,"4","5","M",1);
        edges.add(edge);
        edge=new Edge(6,"5","6","M",1);
        edges.add(edge);
        edge=new Edge(7,"5","7","M",1);
        edges.add(edge);
        edge=new Edge(8,"8","9","M",1);
        edges.add(edge);
        edge=new Edge(9,"9","10","M",1);
        edges.add(edge);
        edge=new Edge(10,"10","10","M",1);
        edges.add(edge);
        edge=new Edge(11,"11","13","M",1);
        edges.add(edge);
        edge=new Edge(12,"12","14","M",1);
        edges.add(edge);
        edge=new Edge(13,"13","15","M",1);
        edges.add(edge);
        edge=new Edge(14,"14","15","M",1);
        edges.add(edge);
        edge=new Edge(15,"15","16","M",1);
        edges.add(edge);
        edge=new Edge(16,"16","18","M",1);
        edges.add(edge);
        edge=new Edge(17,"17","18","M",1);
        edges.add(edge);
        edge=new Edge(18,"18","19","M",1);
        edges.add(edge);
        edge=new Edge(19,"18","20","M",1);
        edges.add(edge);
        edge=new Edge(20,"2","5","M",1);
        edges.add(edge);
        LinkedList<GraphNode> temp,val;
        Node callee;
        for(Node n: nodes){
            if(!graph.containsKey(n.getName())){
                val=new LinkedList<>();
                val.add(new GraphNode(n));
                graph.put(n.getName(),val);
            }
        }
        for(Edge e: edges){
            temp=graph.get(e.getCallerName());
            callee= Project.getNodeByName(e.getCalleeName(),nodes);
            temp.add(new GraphNode(callee));
        }
        Node caller;
        for(Node n:nodes){
            if(!inverseGraph.containsKey(n.getName())){
                val=new LinkedList<>();
                val.add(new GraphNode(n));
                inverseGraph.put(n.getName(),val);
            }
        }
        for(Edge e:edges){
            temp=inverseGraph.get(e.getCalleeName());
            caller=Project.getNodeByName(e.getCallerName(),nodes);
            temp.add(new GraphNode(caller));
        }
        nodeMapper.insertNodeForProject(nodes,1);
        edgeMapper.insertEdgeForProject(edges,1);
    }

    @Test
    public void connectedDomainCountTest(){
        /*
        for(String key:Global.inverseGraph.keySet()){
            LinkedList<GraphNode> nodeLinkedList=Global.inverseGraph.get(key);
            for(GraphNode graphNode:nodeLinkedList){
                System.out.print(graphNode.getNode().getName()+" ");
            }
            System.out.println();
        }
        */
        Assert.assertEquals(3,(int)domainCal.ConnectedDomainCount(0,1).getContent());
    }

    @Test
    public void setThresholdTest(){
        domainCal.setThreshold(0,1);
        List<Domain> domains = domainMapper.getDomainByProjectIdAndCloseness(1,0);
        Assert.assertEquals(3,domains.size());
        Assert.assertEquals(10, nodeMapper.selectNodeByDomain(domains.get(0).getId()).size());
        Assert.assertEquals(7, nodeMapper.selectNodeByDomain(domains.get(1).getId()).size());
        Assert.assertEquals(3, nodeMapper.selectNodeByDomain(domains.get(2).getId()).size());

    }


}
