package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Node_code;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class Node_codeMapperTest {
    @Autowired
    Node_codeMapper node_codeMapper;
    @Test
    public void totalTest(){
        Node_code node_code=new Node_code(0,"public void main");
        int n=node_codeMapper.createNode_code(node_code);
        Assert.assertEquals(1,n);
        node_code=new Node_code(0,"public");
        n=node_codeMapper.updateNode_code(node_code);
        Assert.assertEquals(1,n);
        node_code=node_codeMapper.getNode_Code(0);
        Assert.assertEquals("public",node_code.getCode());

    }

}
