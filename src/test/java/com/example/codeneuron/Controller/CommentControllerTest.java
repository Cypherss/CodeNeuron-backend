package com.example.codeneuron.Controller;


import com.alibaba.fastjson.JSONObject;
import com.example.codeneuron.Controller.Comment.CommentController;
import com.example.codeneuron.PO.Comment;
import com.example.codeneuron.Service.Comment.CommentService;
import com.example.codeneuron.VO.CommentForm;
import com.example.codeneuron.VO.CommentVO;
import com.example.codeneuron.VO.ResponseVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.sql.Timestamp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CommentControllerTest {
    CommentService commentService;
    CommentController commentController;

    @Before
    public void init(){
        this.commentService=mock(CommentService.class);
        this.commentController=new CommentController(this.commentService);
        Timestamp d = new Timestamp(System.currentTimeMillis());
        when(this.commentService.addComment("com.example.test:test()","测试用例代码",1,"user","node",1)).thenReturn(ResponseVO.buildSuccess());
        when(this.commentService.deleteComment(0,1,"domain")).thenReturn(ResponseVO.buildSuccess());
        when(this.commentService.getComment(1,"edge")).thenReturn(ResponseVO.buildSuccess(new CommentVO(new Comment(2,"com.example.test:test()","测试用例代码",1,"user",d,"edge",1))));
//        when(this.commentService.getNodeCommentByName("com.example.test:test()")).thenReturn(ResponseVO.buildSuccess(new CommentVO(2,"com.example.test:test()","测试用例代码","edge")));
        when(this.commentService.updateComment(1,"com.example.test:test()","测试用例代码2")).thenReturn(ResponseVO.buildSuccess());
    }

    @Test
    public void addComment() throws Exception{
        MockMvc mockMvc=standaloneSetup(commentController).build();
        CommentForm commentForm=new CommentForm("com.example.test:test()","测试用例代码",1,"user","node",1);
        mockMvc.perform(post("/comment/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(com.alibaba.fastjson.JSONObject.toJSONString(commentForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteComment() throws Exception{
        MockMvc mockMvc=standaloneSetup(commentController).build();
        mockMvc.perform(get("/comment/delete")
                .param("commentId","0").param("type","domain").param("relatedId","1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getComment() throws Exception{
        MockMvc mockMvc=standaloneSetup(commentController).build();
        MvcResult result=mockMvc.perform(get("/comment/get")
                .param("projectId","1").param("type","edge"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        JSONObject jsonObject=JSONObject.parseObject(result.getResponse().getContentAsString()).getJSONObject("content");
        Assert.assertEquals("com.example.test:test()",jsonObject.get("title"));
        Assert.assertEquals(2,jsonObject.get("id"));
    }

//    @Test
//    public void getNodeCommentByName() throws Exception{
//        MockMvc mockMvc=standaloneSetup(commentController).build();
//        MvcResult result=mockMvc.perform(get("/comment/name/get")
//                .param("name","com.example.test:test()"))
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        JSONObject jsonObject=JSONObject.parseObject(result.getResponse().getContentAsString()).getJSONObject("content");
//        Assert.assertEquals("com.example.test:test()",jsonObject.get("title"));
//        Assert.assertEquals(2,jsonObject.get("id"));
//    }

    @Test
    public void updateComment() throws Exception{
        MockMvc mockMvc=standaloneSetup(commentController).build();
        Timestamp d = new Timestamp(System.currentTimeMillis());
        Comment comment=new Comment(1,"com.example.test:test()","测试用例代码2",1,"user1",d,"node",1);
        CommentVO commentVO = new CommentVO(comment);
        mockMvc.perform(post("/comment/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(com.alibaba.fastjson.JSONObject.toJSONString(commentVO)))
                .andExpect(status().isOk());
    }
}
