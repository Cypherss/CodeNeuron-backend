package com.example.codeneuron.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.codeneuron.Controller.Team.TeamController;
import com.example.codeneuron.Service.Team.TeamService;
import com.example.codeneuron.VO.JoinForm;
import com.example.codeneuron.VO.ResponseVO;
import com.example.codeneuron.VO.TeamForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamControllerTest {
    TeamController teamController;
    TeamService teamService;

    @Before
    public void init(){
        this.teamService=mock(TeamService.class);
        this.teamController=mock(TeamController.class);
        when(this.teamService.createTeamForLeader(new TeamForm("group1",1))).thenReturn(ResponseVO.buildSuccess());
        when(this.teamService.joinTeam(new JoinForm(1,2))).thenReturn(ResponseVO.buildSuccess());
        when(this.teamService.getAllTeamsByLeaderId(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.teamService.getAllTeamsByUserId(2)).thenReturn(ResponseVO.buildSuccess());
        when(this.teamService.getTeamById(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.teamService.getAllUsersByTeamId(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.teamService.getLeaderByTeamId(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.teamService.deleteTeamById(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.teamService.deleteTeamByLeaderId(1)).thenReturn(ResponseVO.buildSuccess());
    }

    @Test
    public void createTeamForLeader() throws Exception{
        MockMvc mockMvc=standaloneSetup(teamController).build();
        TeamForm teamForm=new TeamForm("group1",1);
        mockMvc.perform(post("/team/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(com.alibaba.fastjson.JSONObject.toJSONString(teamForm)))
        .andExpect(status().isOk());
    }

    @Test
    public void getAllUsersByTeamId() throws Exception{
        MockMvc mockMvc=standaloneSetup(teamController).build();
        MvcResult result=mockMvc.perform(get("/team/teammates")
                .param("userId","2"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void joinTeam() throws Exception{
        MockMvc mockMvc=standaloneSetup(teamController).build();
        JoinForm joinForm=new JoinForm(1,1);
        mockMvc.perform(post("/team/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(com.alibaba.fastjson.JSONObject.toJSONString(joinForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void getTeamInfo() throws Exception{
        MockMvc mockMvc=standaloneSetup(teamController).build();
        MvcResult result=mockMvc.perform(get("/team/info")
                .param("id","1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getTeamForUser() throws Exception{
        MockMvc mockMvc=standaloneSetup(teamController).build();
        MvcResult result=mockMvc.perform(get("/team/list")
                .param("userId","2"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getTeamForLeader() throws Exception{
        MockMvc mockMvc=standaloneSetup(teamController).build();
        MvcResult result=mockMvc.perform(get("/team/own")
                .param("leaderId","1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
