package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Comment;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.Service.CalculateService.Dynamic.DomainCal;
import com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentMapperTest {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    DomainCal domainCal;

    @Test
    public void getCommentOfNodesForProject(){
        int before = commentMapper.getCommentOfNodesForProject(1).size();
        for(int i = 0;i < 100;i++){
            Comment comment = new Comment();
            comment.setTitle("New comment"+(i+""));
            comment.setUserId(1);
            comment.setUserName("admin");
            comment.setContent("Content this is content of comment "+(i+""));
            comment.setType("node");
            comment.setRelatedId(i+1);
            commentMapper.createComment(comment);
            commentMapper.createCommentForNode(i+1, comment.getId());
        }
        int after = commentMapper.getCommentOfNodesForProject(1).size();
        Assert.assertEquals(before+100,after);
    }

    @Test
    public void getCommentOfEdgesForProject(){
        int before = commentMapper.getCommentOfEdgesForProject(1).size();
        for(int i = 0;i < 100;i++){
            Comment comment = new Comment();
            comment.setTitle("New comment"+(i+""));
            comment.setUserId(1);
            comment.setUserName("admin");
            comment.setContent("Content this is content of comment "+(i+""));
            comment.setType("edge");
            comment.setRelatedId(i+1);
            commentMapper.createComment(comment);
            commentMapper.createCommentForEdge(i+1, comment.getId());
        }
        int after = commentMapper.getCommentOfEdgesForProject(1).size();
        Assert.assertEquals(before+100,after);
    }

    @Test
    public void getCommentOfDomainsForProject(){
        int before = commentMapper.getCommentOfDomainsForProject(1).size();
        domainCal.setThreshold(0,1);
        int n = ((int)domainCal.getDomainCountByThresholdAndProjectId(0,1).getContent());
        HashMap<Integer, List<Node>> domains = (HashMap<Integer, List<Node>>)domainCal.getDomains(1).getContent();
        Set<Integer> set = domains.keySet();
        Object[] obj = set.toArray();
        Arrays.sort(obj);
        int start = (int)obj[0];
        for(int i = 0;i < n;i++){
            Comment comment = new Comment();
            comment.setTitle("New comment"+(i+""));
            comment.setUserId(1);
            comment.setUserName("admin");
            comment.setContent("Content this is content of comment "+(i+""));
            comment.setType("domain");
            comment.setRelatedId(start+i);
            commentMapper.createComment(comment);
            commentMapper.createCommentForDomain(start+i, comment.getId());
        }
        int after = commentMapper.getCommentOfDomainsForProject(1).size();
        Assert.assertEquals(before+n,after);
    }


}
