package com.example.codeneuron.Controller;

import com.example.codeneuron.Controller.Calculate.DomainController;
import com.example.codeneuron.Service.CalculateService.Dynamic.DomainCal;
import com.example.codeneuron.VO.ResponseVO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.alibaba.fastjson.JSONObject.toJSONString;
public class DomainControllerTest {
    DomainController domainController;
    DomainCal domainCal;

    @Before
    public void init(){
        this.domainCal=mock(DomainCal.class);
        this.domainController=new DomainController(this.domainCal);
        when(this.domainCal.setThreshold(0,1)).thenReturn(ResponseVO.buildSuccess());
        when(this.domainCal.getDomains(1)).thenReturn(ResponseVO.buildSuccess());
        when(this.domainCal.getDomainCountByThresholdAndProjectId(0,1)).thenReturn(ResponseVO.buildSuccess());
    }

    @Test
    public void setThreshold() throws Exception{
        MockMvc mockMvc=standaloneSetup(domainController).build();
        mockMvc.perform(post("/domain/setThreshold")
        .param("threshold","0")
        .param("projectId","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCurrentDomainAndNodes() throws Exception{
        MockMvc mockMvc=standaloneSetup(domainController).build();
        mockMvc.perform(get("/domain/getCurrent")
        .param("projectId","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCount() throws Exception{
        MockMvc mockMvc=standaloneSetup(domainController).build();
        mockMvc.perform(get("/domain/getCount")
        .param("threshold","0")
        .param("projectId","1"))
                .andExpect(status().isOk());
    }
}
