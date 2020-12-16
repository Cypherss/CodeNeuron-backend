package com.example.codeneuron.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.codeneuron.Controller.Calculate.StructureController;
import com.example.codeneuron.Service.CalculateService.Dynamic.StructureCal;
import com.example.codeneuron.VO.ResponseVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StructuralControllerTest {
    StructureCal structureCal;
    @Before
    public void init(){
        this.structureCal=mock(StructureCal.class);
        when(this.structureCal.getProjectStructure(anyInt())).thenReturn(ResponseVO.buildSuccess("0"));
    }

    @Test
    public void getStructure() throws Exception{
        StructureController structureController=new StructureController(this.structureCal);
        MockMvc mockMvc=standaloneSetup(structureController).build();
        MvcResult result=mockMvc.perform(get("/structure")
                .param("id","1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals("0",JSONObject.parseObject(result.getResponse().getContentAsString()).get("content"));
    }
}
