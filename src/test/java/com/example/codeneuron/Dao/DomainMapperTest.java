package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Domain;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DomainMapperTest {

    @Autowired
    DomainMapper domainMapper;
    @Test
    public void getDomainByProjectIdAndCloseness() {
        int before = domainMapper.getDomainByProjectIdAndCloseness(1,0).size();
        for(int i = 0;i<100;i++){
            Domain domain = new Domain();
            domain.setProjectId(1);
            domain.setClosenessThreshold(0);
            domainMapper.insertDomain(domain);
        }
        int after = domainMapper.getDomainByProjectIdAndCloseness(1,0).size();
        Assert.assertEquals(before+100,after);
    }

    @Test
    public void getDomainByProjectId(){
        int before = domainMapper.getDomainByProjectId(1).size();
        for(int i = 0;i<100;i++){
            Domain domain = new Domain();
            domain.setProjectId(1);
            domain.setClosenessThreshold(0);
            domainMapper.insertDomain(domain);
        }
        for(int i = 0;i<100;i++){
            Domain domain = new Domain();
            domain.setProjectId(1);
            domain.setClosenessThreshold(0.5);
            domainMapper.insertDomain(domain);
        }
        int after = domainMapper.getDomainByProjectId(1).size();
        Assert.assertEquals(before+200,after);
    }

    @Test
    public void insertDomain() {
        Domain domain = new Domain();
        domain.setProjectId(1);
        domain.setClosenessThreshold(0);
        domainMapper.insertDomain(domain);
        Assert.assertEquals(1,domainMapper.getDomainById(domain.getId()).getProjectId());
        Assert.assertEquals(0,domainMapper.getDomainById(domain.getId()).getClosenessThreshold(),0.0);
    }

    @Test
    public void deleteDomainByProjectId() {
        domainMapper.deleteDomainByProjectId(1);
        Assert.assertEquals(0,domainMapper.getDomainByProjectIdAndCloseness(1,0).size());
        for(int i = 0;i<100;i++){
            Domain domain = new Domain();
            domain.setProjectId(1);
            domain.setClosenessThreshold(0);
            domainMapper.insertDomain(domain);
        }
        Assert.assertEquals(100, domainMapper.getDomainByProjectIdAndCloseness(1,0).size());
    }
}