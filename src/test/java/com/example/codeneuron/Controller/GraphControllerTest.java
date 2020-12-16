package com.example.codeneuron.Controller;

import com.example.codeneuron.Controller.Calculate.GraphController;
import com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal;
import com.example.codeneuron.VO.RequirementForm;
import com.example.codeneuron.VO.ResponseVO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class GraphControllerTest {
    GraphCal graphCal;
    GraphController graphController;
    RequirementForm requirementForm;

    @Before
    public void init(){
        this.graphCal = mock(GraphCal.class);
        this.graphController = new GraphController(graphCal);
        requirementForm=new RequirementForm("R3","test",0,0);
        when(this.graphCal.NodesCount(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.graphCal.EdgesCount(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.graphCal.requirementTrace(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.graphCal.addRequirement(requirementForm)).thenReturn(ResponseVO.buildSuccess());
        when(this.graphCal.deleteRequirement(1,0,0)).thenReturn(ResponseVO.buildSuccess());
    }

    @Test
    public void addRequirement() throws Exception{
        MockMvc mockMvc=standaloneSetup(graphController).build();
        mockMvc.perform(post("/graph/addRequirement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(com.alibaba.fastjson.JSONObject.toJSONString(requirementForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRequirement() throws Exception{
        MockMvc mockMvc=standaloneSetup(graphController).build();
        mockMvc.perform(get("/graph/deleteRequirement")
                .param("requirementId","1")
                .param("projectId","0")
                .param("nodeId","0"))
                .andExpect(status().isOk());
    }

    @Test
    public void getNodesCount() throws Exception{
        MockMvc mockMvc=standaloneSetup(graphController).build();
        mockMvc.perform(get("/graph/nodecount")
        .param("projectId","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEdgesCount() throws Exception{
        MockMvc mockMvc=standaloneSetup(graphController).build();
        mockMvc.perform(get("/graph/edgecount")
        .param("projectId","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void requirementTraceTest() throws Exception{
        MockMvc mockMvc=standaloneSetup(graphController).build();
        mockMvc.perform(get("/graph/trace")
                .param("projectId","1"))
                .andExpect(status().isOk());
    }
}
