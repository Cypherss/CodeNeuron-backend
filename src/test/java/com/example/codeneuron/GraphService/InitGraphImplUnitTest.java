package com.example.codeneuron.GraphService;

import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.PO.*;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import com.example.codeneuron.ServiceImpl.InitGraph.InitGraphImpl;
import org.apache.ibatis.annotations.Param;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InitGraphImplUnitTest {
    NodeMapper nodeMapper;
    EdgeMapper edgeMapper;
    @Autowired
    InitGraph initGraph;
    @Before
    public void init(){
        nodeMapper=new NodeMapperStub();
        edgeMapper=new EdgeMapperStub();
    }

    @Test
    public void initGraph(){
        List<Node> nodes = nodeMapper.selectAllNodes();
        List<Edge> edges = edgeMapper.selectAllEdges();
        HashMap<String, LinkedList<GraphNode>> graph = initGraph.InitAdjacencyTable(nodes, edges);
        HashMap<String, LinkedList<GraphNode>> inverseGraph = initGraph.InitInverseAdjacencyTable(nodes, edges);
        Assert.assertEquals(21,graph.size());
        Assert.assertEquals(21,inverseGraph.size());
        Assert.assertEquals("2",graph.get("0").get(1).getNode().getName());
        Assert.assertEquals("18",inverseGraph.get("20").get(1).getNode().getName());
    }


}

class NodeMapperStub implements NodeMapper{
    /**
     * 创建新节点
     * @param node
     * @return
     */
    public int createNewNode(Node node){
        return 0;
    }

    /**
     * 删除节点
     * @param id
     * @return
     */
    public int deleteNode(int id){
        return 0;
    }

    /**
     * 根据名字删除节点
     * @param name
     * @return
     */
    public int deleteNodeByName(String name){
        return 0;
    }

    /**
     * 选择所有节点
     * @return
     */
    public List<Node> selectAllNodes(){
        List<Node> nodes=new ArrayList<>();
        Node node;
        for(int i=0;i<21;i++){
            node=new Node(i,i+"",1);
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * 根据id选择节点
     * @param id
     * @return
     */
    public Node selectNodeById(int id){
        return null;
    }

    /**
     * 根据名字选择节点
     * @param name
     * @return
     */
    public Node selectNodeByName(String name){
        return null;
    }
    public Node selectNodeByNameAndProjectId(String name, int projectId){
        return null;
    }
    public List<Node> selectNodeByProjectId(int projectId){return null;};

    public List<Node> selectNodeByDomain(int domainId){return null;};

    public int insertNodeForProject(@Param("list")List<Node> nodeList, int projectId){return 0;};

    public int insertNodeForDomain(@Param("list")List<Node> nodeList, int domainId){return 0;};

    public int deleteNodeForProject(int projectId){return 0;};

    public int deleteNodeForDomain(int domainId){return 0;};

    public int deleteNodeForDomains(@Param("list")List<Domain> domainList){return 0;};

    public int updateNodeX(double x,int nodeId,int projectId){
        return 1;
    }

    public int updateNodeY(double y,int nodeId,int projectId){
        return 1;
    }

    public double getNodeX(int nodeId,int projectId){
        return 0.5;
    }

    public double getNodeY(int nodeId,int projectId){
        return 0.99;
    }

}

class EdgeMapperStub implements EdgeMapper{
    /**
     * 创建新调用关系
     * @param edge
     * @return
     */
    public int createNewEdge(Edge edge){
        return 0;
    }

    /**
     * 删除调用关系
     * @param id
     * @return
     */
    public int deleteEdge(int id){
        return 0;
    }

    /**
     * 选择所有调用关系
     * @return
     */
    public List<Edge> selectAllEdges(){
        Edge edge;
        List<Edge> edges=new ArrayList<>();
        edge=new Edge(0,"0","2","M",1);
        edges.add(edge);
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
        return edges;
    }

    /**
     * 通过id选择调用关系
     * @param id
     * @return
     */
    public Edge selectEdgeById(int id){
        return null;
    }

    /**
     * 通过调用者名和被调用者名选择调用关系
     * @param callerName
     * @param calleeName
     * @return
     */
    public Edge selectEdgeByCallerAndCallee(String callerName, String calleeName){
        return null;
    }
    public List<Edge> selectEdgeByProjectId(int projectId){return null;};

    public int updateEdgeCloseness(List<Edge> edgeList){return 0;};
    public int insertEdgeForProject(List<Edge> edgeList, int projectId){return 0;};

    /**
     * 将project中的所有边删除
     * @param projectId
     * @return
     */
    public int deleteEdgeForProject(int projectId){return 0;};
}
