package com.example.codeneuron.Dao;


import com.example.codeneuron.PO.Edge;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EdgeMapperTest {
    @Autowired
    private EdgeMapper edgeMapper;

    @Test
    public void testSelectNodeById(){
        Assert.assertEquals("edu.ncsu.csc.itrust.BeanBuilder:build(java.util.Map,java.lang.Object)",edgeMapper.selectEdgeById(1).getCallerName());
    }

    @Test
    public void selectEdgeByProjectId() {
        int before = edgeMapper.selectEdgeByProjectId(1).size();
        List<Edge> edgeList = new ArrayList<>();
        for(int i = 0;i<100;i++){
            Edge edge = new Edge();
            edge.setCallerName("Caller"+(i+""));
            edge.setCalleeName("Callee"+(i+""));
            edge.setTypeOfCall("(O)");
            edge.setProjectId(1);
            edgeList.add(edge);
        }
        edgeMapper.insertEdgeForProject(edgeList,1);
        int after = edgeMapper.selectEdgeByProjectId(1).size();
        Assert.assertEquals(before+100,after);
    }

    @Test
    public void updateEdgeCloseness() {
        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Edge edge = new Edge();
            edge.setCallerName("Caller" + (i + ""));
            edge.setCalleeName("Callee" + (i + ""));
            edge.setTypeOfCall("(O)");
            edge.setProjectId(1);
            edgeList.add(edge);
        }
        edgeMapper.insertEdgeForProject(edgeList, 1);
        for (Edge edge : edgeList) {
            edge.setCloseness(0.5);
        }
        Assert.assertEquals(100, edgeMapper.updateEdgeCloseness(edgeList));
        for (Edge edge : edgeList) {
            Assert.assertEquals(edge.getCloseness(), edgeMapper.selectEdgeById(edge.getId()).getCloseness(), 0.0);
        }
    }

    @Test
    public void insertEdgeForProject() {
        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Edge edge = new Edge();
            edge.setCallerName("Caller" + (i + ""));
            edge.setCalleeName("Callee" + (i + ""));
            edge.setTypeOfCall("(O)");
            edge.setProjectId(1);
            edgeList.add(edge);
        }
        Assert.assertEquals(100,edgeMapper.insertEdgeForProject(edgeList, 1));
        for(int i = 0;i<edgeList.size();i++) {
            Edge tmp = edgeMapper.selectEdgeById(edgeList.get(i).getId());
            Assert.assertEquals("Caller"+(i+""),tmp.getCallerName());
            Assert.assertEquals("Callee"+(i+""),tmp.getCalleeName());
            Assert.assertEquals("(O)",tmp.getTypeOfCall());
            Assert.assertEquals(0,tmp.getCloseness(),0.0);
            Assert.assertEquals(1,tmp.getProjectId());
        }
    }

    @Test
    public void deleteEdgeForProject() {
        edgeMapper.deleteEdgeForProject(1);
        Assert.assertEquals(0,edgeMapper.selectEdgeByProjectId(1).size());
        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Edge edge = new Edge();
            edge.setCallerName("Caller" + (i + ""));
            edge.setCalleeName("Callee" + (i + ""));
            edge.setTypeOfCall("(O)");
            edge.setProjectId(1);
            edgeList.add(edge);
        }
        edgeMapper.insertEdgeForProject(edgeList,1);
        Assert.assertEquals(100,edgeMapper.deleteEdgeForProject(1));
        Assert.assertEquals(0,edgeMapper.selectEdgeByProjectId(1).size());
    }
}
