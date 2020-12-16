package com.example.codeneuron.Service.CalculateService.Dynamic;

import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.PO.TreeNode;
import com.example.codeneuron.ServiceImpl.CalculateService.Dynamic.StructureCalImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SructureCalTest {
    @Autowired
    NodeMapper nodeMapper;

    StructureCalImpl structureCalImpl;
    List<Node> nodes;
    @Before
    public void init(){
        structureCalImpl=new StructureCalImpl(nodeMapper);
        nodes=new ArrayList<Node>();
        nodes.add(new Node(0,"com.example.codeneuron.Service.CalculateService.Dynamic.PathCal:searchAllPaths()",0));
        nodes.add(new Node(1,"com.example.codeneuron.Service.CalculateService.Dynamic.PathCal:searchShortestPath()",0));
        nodes.add(new Node(2,"com.example.codeneuron.Dao.EdgeMapper:createNewEdge(Edge edge)",0));
        nodes.add(new Node(3,"com.example.codeneuron.Dao.EdgeMapper:selectAllEdges()",0));
        nodes.add(new Node(4,"com.example.codeneuron.Dao.EdgeMapper:selectEdgeByCallerAndCallee(String callerName, String calleeName)",0));
        nodes.add(new Node(5,"com.example.codeneuron.Dao.NodeMapper:createNewNode(Node node)",0));
        nodes.add(new Node(6,"com.example.codeneuron.Dao.NodeMapper:deleteNodeByName(String name)",0));
        nodes.add(new Node(7,"com.example.codeneuron.PO.Edge:getCallerName()",0));
        nodes.add(new Node(8,"com.example.codeneuron.PO.GraphNode:setCloseness(double closeness)",0));
        nodes.add(new Node(9,"com.example.codeneuron.PO.Global:getNodeByName(String name)",0));
        nodes.add(new Node(10,"com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal:ConnectedDomainCount()",0));
        nodes.add(new Node(11,"com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal:setThreshold(double threshold)",0));
        nodes.add(new Node(12,"com.example.codeneuron.Service.InitGraph.InitGraph:InitInverseAdjacencyTable()",0));
        nodes.add(new Node(13,"com.example.codeneuron.ServiceImpl.CalculateService.PathCalImpl:searchShortestPath()",0));
        nodes.add(new Node(14,"com.example.codeneuron.ServiceImpl.InitGraph.InitGraphImpl:InitAdjacencyTable()",0));
        nodes.add(new Node(15,"com.example.codeneuron.ServiceImpl.InitGraph.InitGraphImpl:InitInverseAdjacencyTable()",0));
        nodes.add(new Node(16,"com.example.codeneuron.ServiceImpl.CalculateService.PathCalImpl:DFS(GraphNode start, String des)",0));
    }

    @After
    public void clear(){
        nodes.clear();
    }

    @Test
    public void buildStructureTest(){
        List<TreeNode> roots=structureCalImpl.builtStructure(nodes);
        Assert.assertEquals(1,roots.size());
        Assert.assertEquals(4,roots.get(0).getChildren().get(0).getChildren().get(0).getChildrenSize());
        for(TreeNode root:roots){
            printTree(root);
        }
    }

    static void printTree(TreeNode root){
        System.out.println(root.getKey());
        if(root.getChildrenSize()==0){
            return;
        }else{
            for(TreeNode child:root.getChildren()){
                printTree(child);
            }
        }
    }
}
