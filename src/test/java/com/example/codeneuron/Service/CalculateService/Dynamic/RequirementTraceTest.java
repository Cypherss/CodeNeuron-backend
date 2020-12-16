package com.example.codeneuron.Service.CalculateService.Dynamic;

import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.Dao.RequirementMapper;
import com.example.codeneuron.Dao.Requirement_traceMapper;
import com.example.codeneuron.PO.Edge;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.PO.Requirement;
import com.example.codeneuron.PO.Requirement_trace;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import com.example.codeneuron.ServiceImpl.CalculateService.Dynamic.GraphCalImpl;
import com.example.codeneuron.VO.RequirementForm;
import com.example.codeneuron.VO.ResponseVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RequirementTraceTest {
    NodeMapper nodeMapper;
    EdgeMapper edgeMapper;
    InitGraph initGraph;
    RequirementMapper requirementMapper;
    Requirement_traceMapper requirement_traceMapper;
    @Before
    public void init(){
        nodeMapper=mock(NodeMapper.class);
        edgeMapper=mock(EdgeMapper.class);
        initGraph=mock(InitGraph.class);
        requirementMapper=mock(RequirementMapper.class);
        requirement_traceMapper=mock(Requirement_traceMapper.class);
    }

    @Test
    public void RequirementTrace(){
        List<Node> nodeList=new ArrayList<>();
        List<Edge> edgeList=new ArrayList<>();
        List<Requirement> requirementList=new ArrayList<>();
        Node nodeTemp;
        nodeTemp=new Node(0,"target",0);
        nodeList.add(nodeTemp);
        nodeTemp=new Node(1,"n1",0);
        nodeList.add(nodeTemp);
        nodeTemp=new Node(2,"n2",0);
        nodeList.add(nodeTemp);
        nodeTemp=new Node(3,"n3",0);
        nodeList.add(nodeTemp);
        Edge edgeTemp;
        edgeTemp=new Edge(0,"n1","target","M",0);
        edgeList.add(edgeTemp);
        edgeTemp=new Edge(1,"n3","target","M",0);
        edgeList.add(edgeTemp);
        edgeTemp=new Edge(2,"target","n2","M",0);
        edgeList.add(edgeTemp);
        Requirement requirementTemp;
        requirementTemp=new Requirement(0,"r1","test",0);
        requirementList.add(requirementTemp);
        requirementTemp=new Requirement(1,"r2","test",0);
        requirementList.add(requirementTemp);
        requirementTemp=new Requirement(2,"r3","test",0);
        requirementList.add(requirementTemp);
        Requirement_trace requirement_traceTemp;
        List<Requirement_trace> n1RList=new ArrayList<>();
        requirement_traceTemp=new Requirement_trace(0,0,1);
        n1RList.add(requirement_traceTemp);
        requirement_traceTemp=new Requirement_trace(1,0,1);
        n1RList.add(requirement_traceTemp);
        List<Requirement_trace> n2RList=new ArrayList<>();
        requirement_traceTemp=new Requirement_trace(1,0,2);
        n2RList.add(requirement_traceTemp);
        List<Requirement_trace> n3RList=new ArrayList<>();
        requirement_traceTemp=new Requirement_trace(2,0,3);
        n3RList.add(requirement_traceTemp);
        requirement_traceTemp=new Requirement_trace(0,0,3);
        n3RList.add(requirement_traceTemp);
        requirement_traceTemp=new Requirement_trace(0,0,0);
        List<Requirement_trace> ntRList=new ArrayList<>();
        ntRList.add(requirement_traceTemp);
        when(nodeMapper.selectNodeByProjectId(0)).thenReturn(nodeList);
        when(edgeMapper.selectEdgeByProjectId(0)).thenReturn(edgeList);
        when(requirementMapper.selectRequirementsByProjectId(0)).thenReturn(requirementList);
        when(requirementMapper.createRequirement(any())).thenReturn(0);
        when(requirement_traceMapper.selectRequirementsByProjectIdAndNodeId(0,1)).thenReturn(n1RList);
        when(requirement_traceMapper.selectRequirementsByProjectIdAndNodeId(0,2)).thenReturn(n2RList);
        when(requirement_traceMapper.selectRequirementsByProjectIdAndNodeId(0,3)).thenReturn(n3RList);
        when(requirement_traceMapper.selectRequirementsByProjectIdAndNodeId(0,0)).thenReturn(ntRList);
        GraphCal graphCal=new GraphCalImpl(nodeMapper,edgeMapper,initGraph,requirementMapper,requirement_traceMapper);
        graphCal.requirementTrace(0);
        verify(requirement_traceMapper,times(4)).createRequirementTrace(any());

    }

    @Test
    public void addRequirement1(){
        RequirementForm requirementForm=new RequirementForm("R1","test",0,0);
        Requirement requirement=new Requirement();
        requirement.setId(0);
        requirement.setName("R1");
        requirement.setDescription("test");
        requirement.setProjectId(0);
        Requirement_trace requirement_trace=new Requirement_trace();
        requirement_trace.setRequirementId(0);
        requirement_trace.setNodeId(0);
        requirement_trace.setProjectId(0);
        when(requirementMapper.selectRequirementByNameAndProjectId("R1",0)).thenReturn(null,requirement);
        when(requirementMapper.createRequirement(anyObject())).thenReturn(0);
        when(requirement_traceMapper.createRequirementTrace(anyObject())).thenReturn(0);
        GraphCal graphCal=new GraphCalImpl(nodeMapper,edgeMapper,initGraph,requirementMapper,requirement_traceMapper);
        graphCal.addRequirement(requirementForm);
        verify(requirement_traceMapper,times(1)).createRequirementTrace(anyObject());
        verify(requirementMapper,times(1)).createRequirement(anyObject());
        verify(requirementMapper,times(2)).selectRequirementByNameAndProjectId(anyString(),anyInt());

    }

    @Test
    public void addRequirement2(){
        RequirementForm requirementForm=new RequirementForm("R1","test",0,0);
        Requirement requirement=new Requirement();
        requirement.setId(0);
        requirement.setName("R1");
        requirement.setDescription("test");
        requirement.setProjectId(0);
        Requirement_trace requirement_trace=new Requirement_trace();
        requirement_trace.setRequirementId(0);
        requirement_trace.setNodeId(0);
        requirement_trace.setProjectId(0);
        when(requirementMapper.selectRequirementByNameAndProjectId("R1",0)).thenReturn(requirement);
        when(requirementMapper.createRequirement(anyObject())).thenReturn(0);
        when(requirement_traceMapper.createRequirementTrace(anyObject())).thenReturn(0);
        GraphCal graphCal=new GraphCalImpl(nodeMapper,edgeMapper,initGraph,requirementMapper,requirement_traceMapper);
        graphCal.addRequirement(requirementForm);
        verify(requirement_traceMapper,times(1)).createRequirementTrace(anyObject());
        verify(requirementMapper,times(0)).createRequirement(anyObject());
        verify(requirementMapper,times(2)).selectRequirementByNameAndProjectId(anyString(),anyInt());

    }

    @Test
    public void deleteRequirement(){
        when(requirement_traceMapper.deleteRequirementTrace(0,0,0)).thenReturn(0);
        GraphCal graphCal=new GraphCalImpl(nodeMapper,edgeMapper,initGraph,requirementMapper,requirement_traceMapper);
        graphCal.deleteRequirement(0,0,0);
        verify(requirement_traceMapper,times(1)).deleteRequirementTrace(0,0,0);

    }
}
