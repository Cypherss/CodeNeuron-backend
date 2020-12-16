package com.example.codeneuron.Controller;

import com.example.codeneuron.Controller.Project.ProjectController;
import com.example.codeneuron.Service.Project.ProjectService;
import com.example.codeneuron.VO.ProjectForm;
import com.example.codeneuron.VO.ResponseVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.alibaba.fastjson.JSONObject.toJSONString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectControllerTest {
    ProjectService projectService;
    ProjectController projectController;

    @Before
    public void init(){
        this.projectService=mock(ProjectService.class);
        this.projectController=new ProjectController(this.projectService);
        when(this.projectService.getProjectById(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.projectService.getAllProjectsByUserId(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.projectService.deleteProjectById(1)).thenReturn(ResponseVO.buildSuccess(1));
        when(this.projectService.getEdgeForProject(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.projectService.getNodeForProject(1)).thenReturn(ResponseVO.buildSuccess());
    }

    @Test
    public void getProjectInfo() throws Exception{
        MockMvc mockMvc=standaloneSetup(projectController).build();
        mockMvc.perform(get("/project/info")
                .param("id","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectForTeam() throws Exception{
        MockMvc mockMvc=standaloneSetup(projectController).build();
        mockMvc.perform(get("/project/team")
        .param("teamId","1")).andExpect(status().isOk());
    }
    @Test
    public void getProjectForUser() throws Exception{
        MockMvc mockMvc=standaloneSetup(projectController).build();
        mockMvc.perform(get("/project/user")
                .param("userId","1")).andExpect(status().isOk());
    }

    @Test
    public void deleteProjectById() throws Exception{
        MockMvc mockMvc=standaloneSetup(projectController).build();
        mockMvc.perform(delete("/project/delete")
        .param("projectId","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void createProjectForUser() throws Exception{
        ProjectForm projectForm= new ProjectForm("Project1",1,0);
        MockMvc mockMvc=standaloneSetup(projectController).build();
        mockMvc.perform(post("/project/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJSONString(projectForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void getEdgeForProject() throws Exception{
        MockMvc mockMvc=standaloneSetup(projectController).build();
        mockMvc.perform(get("/project/edges")
        .param("projectId","1"))
        .andExpect(status().isOk());
    }

    @Test
    public void getNodeForProject() throws Exception{
        MockMvc mockMvc=standaloneSetup(projectController).build();
        mockMvc.perform(get("/project/nodes")
        .param("projectId","1"))
                .andExpect(status().isOk());
    }
}
