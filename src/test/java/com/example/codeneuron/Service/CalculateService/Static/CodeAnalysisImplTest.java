package com.example.codeneuron.Service.CalculateService.Static;

import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.Dao.Node_codeMapper;
import com.example.codeneuron.PO.Edge;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.Service.CalculateService.Dynamic.DomainCal;
import com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import com.example.codeneuron.ServiceImpl.CalculateService.Static.CodeAnalysisImpl;
import com.example.codeneuron.VO.ResponseVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.*;

public class CodeAnalysisImplTest {

    EdgeMapper edgeMapper;
    NodeMapper nodeMapper;
    Node_codeMapper node_codeMapper;
    GraphCal graphCal;
    DomainCal domainCal;
    InitGraph initGraph;
    @Before
    public void init(){
        graphCal=mock(GraphCal.class);
        domainCal=mock(DomainCal.class);
        initGraph=mock(InitGraph.class);
    }
    @Test
    public void saveCode(){
        node_codeMapper=mock(Node_codeMapper.class);
        nodeMapper=mock(NodeMapper.class);
        when(node_codeMapper.createNode_code(anyObject())).thenReturn(0);
        when(nodeMapper.selectNodeByProjectId(anyInt())).thenReturn(ceateNodeList());
        when(nodeMapper.selectNodeByName("com.example.code.account:hello(java.String)")).thenReturn(new Node(0,"com.example.code.account:hello(java.String)",1));
        when(nodeMapper.selectNodeByName("com.example.code.account:hello(java.String name, com.example.cpde.po.Node)")).thenReturn(new Node(1,"com.example.code.account:hello(java.String name, com.example.cpde.po.Node)",1));
        when(nodeMapper.selectNodeByName("com.example.code.project:hello()")).thenReturn(new Node(2,"com.example.code.project:hello()",1));
        when(nodeMapper.selectNodeByName("com.example.code.PO:thank(java.String, java.math.double)")).thenReturn(new Node(3,"com.example.code.PO:thank(java.String, java.math.double)",1));
        when(nodeMapper.selectNodeByName("com.example.code.PO:thank(java.String,com.example.cpde.po.PO)")).thenReturn(new Node(4,"com.example.code.PO:thank(java.String,com.example.cpde.po.PO)",1));
        when(nodeMapper.selectNodeByName("com.example.code.comment:hello(java.String)")).thenReturn(new Node(5,"com.example.code.comment:hello(java.String)",1));
        when(nodeMapper.selectNodeByName("com.example.code.account:thankyou()")).thenReturn(new Node(6,"com.example.code.account:thankyou()",1));
        when(nodeMapper.selectNodeByName("com.example.code.heihei:hello(java.dao)")).thenReturn(new Node(7,"com.example.code.heihei:hello(java.dao)",1));
        List<List<String>> functions=new ArrayList<>();
        List<String> function=new ArrayList<>();
        function.add("com.example.code.account:hello(String name)");
        function.add("hello world");
        functions.add(function);
        function=new ArrayList<>();
        function.add("com.example.code.account:hello(String name,Node node)");
        function.add("node");
        functions.add(function);
        function=new ArrayList<>();
        function.add("com.example.code.project:hello()");
        function.add("project");
        functions.add(function);
        function=new ArrayList<>();
        function.add("com.example.code.PO:thank(String hello,double you)");
        function.add("thank1");
        functions.add(function);
        function=new ArrayList<>();
        function.add("com.example.code.PO:thank(String hello,PO you)");
        function.add("thank2");
        functions.add(function);
        CodeAnalysis codeAnalysis=new CodeAnalysisImpl(nodeMapper,edgeMapper,node_codeMapper,domainCal,initGraph,graphCal);
        ((CodeAnalysisImpl) codeAnalysis).saveCode(functions,1);
    }

    List<Node> ceateNodeList(){
        List<Node> nodes=new ArrayList<>();
        Node node;
        node=new Node(0,"com.example.code.account:hello(java.String)",1);
        nodes.add(node);
        node=new Node(1,"com.example.code.account:hello(java.String name, com.example.cpde.po.Node)",1);
        nodes.add(node);
        node=new Node(2,"com.example.code.project:hello()",1);
        nodes.add(node);
        node=new Node(3,"com.example.code.PO:thank(java.String, java.math.double)",1);
        nodes.add(node);
        node=new Node(4,"com.example.code.PO:thank(java.String,com.example.cpde.po.PO)",1);
        nodes.add(node);
        node=new Node(5,"com.example.code.comment:hello(java.String)",1);
        nodes.add(node);
        node=new Node(6,"com.example.code.account:thankyou()",1);
        nodes.add(node);
        node=new Node(7,"com.example.code.heihei:hello(java.dao)",1);
        nodes.add(node);
        return nodes;
    }

    @Test
    public void codeAnalysis() throws Exception{
        String file1="src/test/resources/backend-codeneuron.zip";
        String file2="src/test/resources/iTrust_v13.zip";
        File file=new File(file2);
        InputStream inputStream=new FileInputStream(file);
        MultipartFile multipartFile=new MockMultipartFile(file.getName(),inputStream);
        CodeAnalysisImpl codeAnalysisImpl=new CodeAnalysisImpl(nodeMapper,edgeMapper,node_codeMapper,domainCal,initGraph,graphCal);
        List<List<String>> functions=(List<List<String>>)codeAnalysisImpl.getFunctionSourceCode(multipartFile.getInputStream());
//        for(List<String> func:functions){
//            System.out.println("function:");
//            for(String s:func){
//                System.out.println(s);
//            }
//            System.out.println("===========================");
//        }
        Assert.assertEquals(3960,functions.size());
    }

    @Test
    public void generateNodesAndEdges(){
        nodeMapper=mock(NodeMapper.class);
        edgeMapper=mock(EdgeMapper.class);
        List<String> dependencies=new ArrayList<>();
        dependencies.add("M:A (S)B");
        dependencies.add("M:A (S)C");
        dependencies.add("M:A (S)B");
        dependencies.add("M:C (S)D");
        when(nodeMapper.insertNodeForProject(anyList(),anyInt())).thenReturn(0);
        when(edgeMapper.createNewEdge(anyObject())).thenReturn(0);
        when(graphCal.ClosenessCalculate(eq(1),anyObject(),anyObject())).thenReturn(new ResponseVO());
        when(edgeMapper.updateEdgeCloseness(anyList())).thenReturn(0);
        when(domainCal.setThreshold(0,1)).thenReturn(new ResponseVO());
        CodeAnalysisImpl codeAnalysisImpl=new CodeAnalysisImpl(nodeMapper,edgeMapper,node_codeMapper,domainCal,initGraph,graphCal);
        codeAnalysisImpl.generateNodesAndEdges(dependencies,1);
        verify(nodeMapper,times(1)).insertNodeForProject(anyObject(),anyInt());
        verify(edgeMapper,times(1)).insertEdgeForProject(anyObject(),anyInt());


    }

    @Test
    public void fileTransfer() throws Exception{
        CodeAnalysisImpl codeAnalysisImpl=new CodeAnalysisImpl(nodeMapper,edgeMapper,node_codeMapper,domainCal,initGraph,graphCal);
        File file=new File("src/test/resources/backend-codeneuron.zip");
        InputStream inputStream=new FileInputStream(file);
        MultipartFile multipartFile=new MockMultipartFile(file.getName(),inputStream);
        File f=codeAnalysisImpl.fileTransfer(multipartFile);
        Assert.assertEquals("class java.io.File",f.getClass().toString());
        File tempFile = new File(f.toURI());
        System.out.println(f.toURI());
        if (tempFile.delete()){
            System.out.println("删除成功 ");
        }else {
            System.out.println("删除失败 ");

        }
    }



}
