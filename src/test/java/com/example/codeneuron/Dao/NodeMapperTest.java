package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Domain;
import com.example.codeneuron.PO.Edge;
import com.example.codeneuron.PO.Node;
import org.junit.Assert;
import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class NodeMapperTest {
    @Autowired
    private NodeMapper nodeMapper;
    @Autowired
    private DomainMapper domainMapper;

    @Test
    public void testSelectNodeById(){
        Assert.assertEquals("edu.ncsu.csc.itrust.BeanBuilder:build(java.util.Map,java.lang.Object)",nodeMapper.selectNodeById(1).getName());
    }

    @Test
    public void selectNodeByProjectId() {
        int before = nodeMapper.selectNodeByProjectId(1).size();
        for(int i = 0;i<100;i++){
            Node tmp = new Node();
            tmp.setName("Node"+(i+""));
            tmp.setProjectId(1);
            nodeMapper.createNewNode(tmp);
        }
        int after = nodeMapper.selectNodeByProjectId(1).size();
        Assert.assertEquals(before+100,after);
        List<Node> nodes=nodeMapper.selectNodeByProjectId(1);
        System.out.println(nodes.get(0).getX());
    }

    @Test
    public void selectNodeByDomain() {
        int before = nodeMapper.selectNodeByDomain(1).size();
        List<Node> domainNode = new ArrayList<>();
        for(int i = 1;i<101;i++){
            Node tmp = nodeMapper.selectNodeById(i);
            domainNode.add(tmp);
        }
        Assert.assertEquals(100,nodeMapper.insertNodeForDomain(domainNode,1));
        int after = nodeMapper.selectNodeByDomain(1).size();
        System.out.println(after);
        Assert.assertEquals(1835,after);
    }

    @Test
    public void insertNodeForProject() {
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Node node = new Node();
            node.setName("Node"+(i+""));
            node.setProjectId(1);
            nodeList.add(node);
        }
        Assert.assertEquals(100,nodeMapper.insertNodeForProject(nodeList, 1));
        for(int i = 0;i<nodeList.size();i++) {
            Node tmp = nodeMapper.selectNodeById(nodeList.get(i).getId());
            Assert.assertEquals("Node"+(i+""),tmp.getName());
            Assert.assertEquals(1,tmp.getProjectId());
        }
    }

    @Test
    public void insertNodeForDomain() {
        int before = nodeMapper.selectNodeByDomain(1).size();
        List<Node> nodeList = new ArrayList<>();
        for(int i = 1;i<101;i++){
            nodeList.add(nodeMapper.selectNodeById(i));
        }
        Assert.assertEquals(100,nodeMapper.insertNodeForDomain(nodeList,1));
//        int after = nodeMapper.selectNodeByDomain()
    }

    @Test
    public void deleteNodeForProject() {
        nodeMapper.deleteNodeForProject(1);
        Assert.assertEquals(0,nodeMapper.selectNodeByProjectId(1).size());
        List<Node> nodes = new ArrayList<>();
        for(int i = 0;i<100;i++){
            Node node = new Node();
            node.setName("Node"+(""+i));
            node.setProjectId(1);
            nodes.add(node);
        }
        nodeMapper.insertNodeForProject(nodes,1);
        int before = nodeMapper.selectNodeByProjectId(1).size();
        Assert.assertEquals(100,nodeMapper.deleteNodeForProject(1));
        int after = nodeMapper.selectNodeByProjectId(1).size();
        Assert.assertEquals(before-100,after);

    }

    @Test
    public void deleteNodeForDomain() {
        nodeMapper.deleteNodeForDomain(1);
        Assert.assertEquals(0,nodeMapper.selectNodeByDomain(1).size());
        List<Node> nodes = new ArrayList<>();
        for(int i = 0;i<100;i++){
            Node node = new Node();
            node.setName("Node"+(""+i));
            node.setProjectId(1);
            nodes.add(node);
        }
        nodeMapper.insertNodeForProject(nodes,1); //只有这个方法才是插入到node表中的
        nodeMapper.insertNodeForDomain(nodes,1);//这个方法只是增加映射的
        Assert.assertEquals(100,nodeMapper.selectNodeByDomain(1).size());
        nodeMapper.deleteNodeForDomain(1);
        Assert.assertEquals(0,nodeMapper.selectNodeByDomain(1).size());
    }

    @Test
    public void deleteNodeForDomains(){
        nodeMapper.deleteNodeForProject(1);
        Assert.assertEquals(0,nodeMapper.selectNodeByProjectId(1).size());
        List<Domain> domains = new ArrayList<>();
        for(int i = 0;i<50;i++){
            Domain domain = new Domain();
            domain.setProjectId(1);
            domain.setClosenessThreshold(0);
            domains.add(domain);
            domainMapper.insertDomain(domain);
            List<Node> nodes = new ArrayList<>();
            for(int j = 0;j<100;j++){
                Node node = new Node();
                node.setProjectId(1);
                node.setName("Node"+(""+i));
                nodes.add(node);
            }
            nodeMapper.insertNodeForProject(nodes,1);
            nodeMapper.insertNodeForDomain(nodes, domain.getId());
            Assert.assertEquals(100,nodeMapper.selectNodeByDomain(domain.getId()).size());
        }
        nodeMapper.deleteNodeForDomains(domains);
        int total = 0;
        for(Domain domain:domains){
            total += nodeMapper.selectNodeByDomain(domain.getId()).size();
        }
        Assert.assertEquals(0, total);
    }

    @Test
    public void getSetXTest(){
        nodeMapper.updateNodeX(0.5,1,1);
        Assert.assertTrue(0.5==nodeMapper.getNodeX(1,1));
    }

    @Test
    public void getSetYTest(){
        nodeMapper.updateNodeY(0.99,2,1);
        Assert.assertTrue(0.99==nodeMapper.getNodeY(2,1));
    }
}
