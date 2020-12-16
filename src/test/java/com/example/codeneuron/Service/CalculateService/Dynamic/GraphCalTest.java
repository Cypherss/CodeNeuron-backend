package com.example.codeneuron.Service.CalculateService.Dynamic;

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

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GraphCalTest {
    /**
     * GraphCalImpl Tester.
     *
     * @author tintin
     * @since <pre>Feb 26, 2020</pre>
     * @version 1.0
     */
    @Autowired
    GraphCal graphCal;
    @Autowired
    DomainCal domainCal;
    @Autowired
    EdgeMapper edgeMapper;
    @Autowired
    NodeMapper nodeMapper;
    List<Node> nodes;
    List<Edge> edges;
    HashMap<String, LinkedList<GraphNode>> graph;
    HashMap<String, LinkedList<GraphNode>> inverseGraph;
        @Before
        public void before() throws Exception {
            Node node;
            Edge edge;
            nodes = new ArrayList<>();
            edges = new ArrayList<>();
            graph = new HashMap<>();
            inverseGraph = new HashMap<>();
            edgeMapper.deleteEdgeForProject(1);
            nodeMapper.deleteNodeForProject(1);
            for(int i=0;i<12;i++){
                node=new Node(i,Character.toString((char)(97+i)),1);
                nodes.add(node);
            }
            edge=new Edge(0,"a","b","M",1);
            edges.add(edge);
            edge=new Edge(1,"b","c","M",1);
            edges.add(edge);
            edge=new Edge(2,"a","c","M",1);
            edges.add(edge);
            edge=new Edge(3,"d","a","M",1);
            edges.add(edge);
            edge=new Edge(4,"d","b","M",1);
            edges.add(edge);
            edge=new Edge(5,"e","b","M",1);
            edges.add(edge);
            edge=new Edge(6,"e","d","M",1);
            edges.add(edge);
            edge=new Edge(7,"c","e","M",1);
            edges.add(edge);
            edge=new Edge(8,"f","g","M",1);
            edges.add(edge);
            edge=new Edge(9,"g","g","M",1);
            edges.add(edge);
            edge=new Edge(10,"j","i","M",1);
            edges.add(edge);
            edge=new Edge(11,"i","j","M",1);
            edges.add(edge);
            edge=new Edge(12,"i","k","M",1);
            edges.add(edge);
            edge=new Edge(13,"k","l","M",1);
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

    /**
     * Method: ClosenessCalculate()
     */
    @Test
    public void testClosenessCalculate() throws Exception{
        graphCal.ClosenessCalculate(1,graph,inverseGraph);

        /*
        for(int i=0;i<12;i++){
            LinkedList<GraphNode> tempGraphNodes=Global.graph.get(Character.toString((char)(97+i)));
            for(GraphNode g:tempGraphNodes){
                if(!g.equals(tempGraphNodes.getFirst())){
                    System.out.println(Character.toString((char)(97+i))+"->"+g.getNode().getName()+g.getCloseness());
                }
            }
        }*/
        Assert.assertEquals((double)2/5,(double)graph.get("a").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/4,(double)graph.get("a").get(2).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)graph.get("b").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/2,(double)graph.get("c").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)graph.get("d").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/5,(double)graph.get("d").get(2).getCloseness(),0.00);
        Assert.assertEquals((double)2/5,(double)graph.get("e").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)graph.get("e").get(2).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)graph.get("f").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)graph.get("g").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)graph.get("i").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)graph.get("i").get(2).getCloseness(),0.00);
        Assert.assertEquals((double)2/2,(double)graph.get("j").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/2,(double)graph.get("k").get(1).getCloseness(),0.00);

        Assert.assertEquals((double)2/3,(double)inverseGraph.get("a").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/5,(double)inverseGraph.get("b").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/5,(double)inverseGraph.get("b").get(2).getCloseness(),0.00);
        Assert.assertEquals((double)2/5,(double)inverseGraph.get("b").get(3).getCloseness(),0.00);
        Assert.assertEquals((double)2/4,(double)inverseGraph.get("c").get(2).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)inverseGraph.get("c").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)inverseGraph.get("d").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/2,(double)inverseGraph.get("e").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)inverseGraph.get("g").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)inverseGraph.get("g").get(2).getCloseness(),0.00);
        Assert.assertEquals((double)2/2,(double)inverseGraph.get("i").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)inverseGraph.get("j").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/3,(double)inverseGraph.get("k").get(1).getCloseness(),0.00);
        Assert.assertEquals((double)2/2,(double)inverseGraph.get("l").get(1).getCloseness(),0.00);
    }

    /**
     *
     * Method: ConnectedDomainCount()
     *
     */
    @Test
    public void testConnectedDomainCount() throws Exception {
        Assert.assertEquals(4,(int)domainCal.ConnectedDomainCount(0,1).getContent());
    }

    /**
     *
     * Method:TopologyCalculate()
     *
     */
    @Test
    public void testTopologyCalculate() throws Exception{
        Assert.assertEquals(9,(int)domainCal.ConnectedDomainCount(0.7,1).getContent());
        Assert.assertEquals(3,((List<Set<Node>>)domainCal.TopologyCalculate(0.7,1).getContent()).size());
    }




}
