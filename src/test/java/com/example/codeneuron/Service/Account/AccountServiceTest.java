package com.example.codeneuron.Service.Account;

import com.example.codeneuron.Dao.AccountMapper;
import com.example.codeneuron.PO.Admin;
import com.example.codeneuron.PO.User;
import com.example.codeneuron.VO.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountServiceTest {
    UserForm userForm;
    AdminForm adminForm;
    User user;
    Admin admin;

    @Autowired
    AccountService accountService;
    @Autowired
    AccountMapper accountMapper;

    @Before
    public void init(){
        userForm=new UserForm();
        adminForm=new AdminForm();
        userForm.setName("tintin");
        userForm.setPassword("9813242");
        adminForm.setName("addmin");
        adminForm.setPassword("123456");
        user=new User();
        admin=new Admin();
        user.setName(userForm.getName());
        user.setPassword(userForm.getPassword());
        admin.setName(adminForm.getName());
        admin.setPassword(adminForm.getPassword());
    }

    @Test
    public void registerUserTest(){
        accountService.registerUser(userForm);
        List<User> userList=(List<User>) (((ResponseVO)accountService.getAllUsers()).getContent());
        User temp=new User();
        for(User user:userList){
            if(user.getName().equals("tintin")){
                temp=user;
            }
        }
        Assert.assertEquals("tintin",temp.getName());
        Assert.assertEquals("9813242",temp.getPassword());
    }

    @Test
    public void registerAdminTest(){
        accountService.registerAdmin(adminForm);
        List<Admin> adminList=(List<Admin>) (((ResponseVO)accountService.getAllAdmins()).getContent());
        Admin temp=new Admin();
        for(Admin admin:adminList){
            if(admin.getName().equals("addmin")){
                temp=admin;
            }
        }
        Assert.assertEquals("addmin",temp.getName());
        Assert.assertEquals("123456",temp.getPassword());
    }

    @Test
    public void loginUser(){
        accountMapper.createNewUser(user);
        UserVO userVO=accountService.loginUser(userForm);
        Assert.assertEquals(user.getName(),userVO.getName());
        Assert.assertEquals(user.getPassword(),userVO.getPassword());
    }

    @Test
    public void loginAdmin(){
        accountMapper.createNewAdmin(admin);
        AdminVO adminVO=accountService.loginAdmin(adminForm);
        Assert.assertEquals(admin.getName(),adminVO.getName());
        Assert.assertEquals(admin.getPassword(),adminVO.getPassword());
    }


    @Test
    public void getAllAdmins(){
        List<Admin> adminsorigin=(List<Admin>)accountService.getAllAdmins().getContent();
        int number = adminsorigin.size();
        for(int i=0;i<20;i++){
            Admin admin=new Admin();
            admin.setName("addmin"+i);
            admin.setPassword("12345"+i);
            accountMapper.createNewAdmin(admin);
        }
        List<Admin> admins=(List<Admin>)accountService.getAllAdmins().getContent();
        Assert.assertEquals(20+number,admins.size());
    }
}
