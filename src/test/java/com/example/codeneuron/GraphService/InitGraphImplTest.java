package com.example.codeneuron.GraphService;

import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.PO.Edge;
import com.example.codeneuron.PO.Global;
import com.example.codeneuron.PO.GraphNode;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InitGraphImplTest {


    @Autowired
    EdgeMapper edgeMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    InitGraph initGraph;

    @Test
    public void testInitAdjacencyTable(){
        List<Node> nodes=nodeMapper.selectNodeByProjectId(1);
        List<Edge> edges=edgeMapper.selectEdgeByProjectId(1);

        HashMap<String, LinkedList<GraphNode>> graph = initGraph.InitAdjacencyTable(nodes,edges);
        Assert.assertEquals(1982, graph.keySet().size());
        boolean flag=false;
//        System.out.println(graph);
        for(GraphNode graphNode:graph.get("edu.ncsu.csc.itrust.BeanBuilder:build(java.util.Map,java.lang.Object)")){
            if(graphNode.getNode().getName().equals("edu.ncsu.csc.itrust.BeanBuilder:checkOverloadedMethods(java.lang.Object)")){
                flag=true;
                break;
            }
        }
        Assert.assertEquals(true,flag);

    }
    @Test
    public void testInitInverseAdjacencyTable(){
        List<Node> nodes=nodeMapper.selectNodeByProjectId(1);
        List<Edge> edges=edgeMapper.selectEdgeByProjectId(1);

        HashMap<String, LinkedList<GraphNode>> inverseGraph = initGraph.InitInverseAdjacencyTable(nodes,edges);
        Assert.assertEquals(1982,inverseGraph.keySet().size());
    }


}

