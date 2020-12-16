package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Requirement;
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
public class RequirementMapperTest {
    @Autowired
    RequirementMapper requirementMapper;


    @Test
    public void createAndDeleteTest(){
        List<Requirement> requirements=requirementMapper.selectRequirementsByProjectId(0);
        int size=requirements.size();
        Requirement requirement=new Requirement();
        requirement.setName("R3");
        requirement.setDescription("test");
        requirement.setProjectId(0);
        requirementMapper.createRequirement(requirement);
        Assert.assertEquals(size+1,requirementMapper.selectRequirementsByProjectId(0).size());
        requirementMapper.deleteRequirement(2);
        Assert.assertEquals(size,requirementMapper.selectRequirementsByProjectId(0).size());
    }

    @Test
    public void selectRequirementById(){
        Requirement requirement=requirementMapper.selectRequirementById(2);
        Assert.assertEquals("R2",requirement.getName());
    }

    @Test
    public void selectRequirementByNameAndProjectId(){
        Requirement requirement=requirementMapper.selectRequirementByNameAndProjectId("R1",0);
        Assert.assertNotNull(requirement);
    }

    @Test
    public void updateTest(){
        Requirement requirement=requirementMapper.selectRequirementById(1);
        requirement.setName("NR1");
        requirementMapper.updateRequirement(requirement);
        Assert.assertEquals("NR1",requirementMapper.selectRequirementById(1).getName());
    }
}
