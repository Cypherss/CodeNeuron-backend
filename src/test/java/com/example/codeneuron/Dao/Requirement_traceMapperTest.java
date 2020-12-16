package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Requirement_trace;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class Requirement_traceMapperTest {
    @Autowired
    Requirement_traceMapper requirement_traceMapper;

    @Test
    public void createAndSelectAndDeleteTest(){
        List<Requirement_trace> requirement_traces=requirement_traceMapper.selectRequirementsByProjectIdAndNodeId(0,0);
        int size=requirement_traces.size();
        Requirement_trace requirement_trace=new Requirement_trace(4,0,0);
        requirement_traceMapper.createRequirementTrace(requirement_trace);
        Assert.assertEquals(size+1,requirement_traceMapper.selectRequirementsByProjectIdAndNodeId(0,0).size());
        requirement_traceMapper.deleteRequirementTrace(4,0,0);
        Assert.assertEquals(size,requirement_traceMapper.selectRequirementsByProjectIdAndNodeId(0,0).size());

    }

    @Test
    public void selectByProjectIdAndRequirementId(){
        Requirement_trace requirement_trace=new Requirement_trace(1,0,1);
        requirement_traceMapper.createRequirementTrace(requirement_trace);
        List<Requirement_trace> requirement_traces=requirement_traceMapper.selectRequirementsByProjectIdAndRequirementId(0,1);
        Assert.assertEquals(2,requirement_traces.size());
    }

}
