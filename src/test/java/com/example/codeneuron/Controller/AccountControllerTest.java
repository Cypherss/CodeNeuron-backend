package com.example.codeneuron.Controller;

import com.example.codeneuron.Controller.Account.AccountController;
import com.example.codeneuron.PO.Admin;
import com.example.codeneuron.PO.User;
import com.example.codeneuron.Service.Account.AccountService;
import com.example.codeneuron.VO.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static com.alibaba.fastjson.JSONObject.toJSONString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AccountControllerTest {
    AccountService accountService;
    AccountController accountController;

    @Before
    public void init(){
        this.accountService=mock(AccountService.class);
        this.accountController=mock(AccountController.class);
        User user=new User("tintin","123456");
        Admin admin=new Admin("addmin","gggggg");
        when(accountService.registerUser(new UserForm("tintin","123456"))).thenReturn(ResponseVO.buildSuccess());
        when(accountService.registerAdmin(new AdminForm("addmin","gggggg"))).thenReturn(ResponseVO.buildSuccess());
        when(accountService.loginUser(new UserForm("tintin","123456"))).thenReturn(new UserVO(user));
        when(accountService.loginAdmin(new AdminForm("addmin","gggggg"))).thenReturn(new AdminVO(admin));
        when(accountService.getAllAdmins()).thenReturn(ResponseVO.buildSuccess());
        when(accountService.getAllUsers()).thenReturn(ResponseVO.buildSuccess());
    }

    @Test
    public void loginUserTest() throws Exception{
        UserForm userForm=new UserForm("tintin","123456");
        MockMvc mockMvc=standaloneSetup(accountController).build();
        mockMvc.perform(post("/user/loginUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(com.alibaba.fastjson.JSONObject.toJSONString(userForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void loginAdminTest() throws Exception{
        AdminForm adminForm=new AdminForm("addmin","gggggg");
        MockMvc mockMvc=standaloneSetup(accountController).build();
        mockMvc.perform(post("/user/loginAdmin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(com.alibaba.fastjson.JSONObject.toJSONString(adminForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void registerUserTest() throws Exception{
        UserForm userForm=new UserForm("tintin","123456");
        MockMvc mockMvc=standaloneSetup(accountController).build();
        mockMvc.perform(post("/user/registerUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(com.alibaba.fastjson.JSONObject.toJSONString(userForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void registerAdminTest() throws Exception{
        AdminForm adminForm=new AdminForm("addmin","gggggg");
        MockMvc mockMvc=standaloneSetup(accountController).build();
        mockMvc.perform(post("/user/registerAdmin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(com.alibaba.fastjson.JSONObject.toJSONString(adminForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUserTest() throws Exception{
        MockMvc mockMvc=standaloneSetup(accountController).build();
            mockMvc.perform(get("/user/allUser"))
                    .andExpect(status().isOk());
    }

    @Test
    public void getAllAdminTest() throws Exception{
        MockMvc mockMvc=standaloneSetup(accountController).build();
        mockMvc.perform(get("/user/allAdmin"))
                .andExpect(status().isOk());
    }
}
