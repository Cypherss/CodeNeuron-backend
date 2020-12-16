package com.example.codeneuron.Controller;


import com.example.codeneuron.Controller.Calculate.PathController;
import com.example.codeneuron.Service.CalculateService.Dynamic.PathCal;
import com.example.codeneuron.VO.ResponseVO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PathControllerTest {
    PathCal pathCal;
    PathController pathController;
    @Before
    public void init(){
        this.pathCal =mock(PathCal.class);
        this.pathController=new PathController(this.pathCal);
        when(this.pathCal.searchAllPaths("a","b",1)).thenReturn(ResponseVO.buildSuccess());
        when(this.pathCal.searchShortestPath("a","b",1)).thenReturn(ResponseVO.buildSuccess());
        when(this.pathCal.getFunctionNameLike("a",1)).thenReturn(ResponseVO.buildSuccess());
    }

    @Test
    public void getAllPaths() throws Exception{
        MockMvc mockMvc=standaloneSetup(pathController).build();
        mockMvc.perform(get("/path/all")
                .param("source","a")
                .param("target","b")
                .param("projectId","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getShortestPath() throws Exception{
        MockMvc mockMvc=standaloneSetup(pathController).build();
        mockMvc.perform(get("/path/shortest")
                .param("source","a")
                .param("target","b")
                .param("projectId","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFunctionNameLike() throws Exception{
        MockMvc mockMvc=standaloneSetup(pathController).build();
        mockMvc.perform(get("/path/namelike")
        .param("name","a")
        .param("projectId","1"))
              .andExpect(status().isOk());
    }

}
