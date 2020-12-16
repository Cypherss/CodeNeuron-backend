package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Admin;
import com.example.codeneuron.PO.User;
import com.example.codeneuron.VO.UserForm;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountMapperTest {
    @Autowired
    AccountMapper accountMapper;

    @Test
    public void createNewUserTest(){
        User user=new User("tintin","123456");
        Assert.assertEquals(1,accountMapper.createNewUser(user));
    }

    @Test
    public void createAdminTest(){
        Admin admin=new Admin("addmin","gggggg");
        Assert.assertEquals(1,accountMapper.createNewAdmin(admin));
    }

    @Test
    public void selectAllUsersTest(){
        int before = accountMapper.selectAllUsers().size();
        for(int i = 0;i<20;i++){
            User user=new User("tintin"+i,"12345"+i);
            accountMapper.createNewUser(user);
        }
        int after = accountMapper.selectAllUsers().size();
        Assert.assertEquals(before+20,after);
    }

    @Test
    public void setlectAllAdminsTest(){
        int before = accountMapper.selectAllAdmins().size();
        for(int i = 0;i<20;i++){
            Admin admin=new Admin("addmin"+i,"123453"+i);
            accountMapper.createNewAdmin(admin);
        }
        int after = accountMapper.selectAllAdmins().size();
        Assert.assertEquals(before+20,after);
    }

    @Test
    public void getUserByNameTest(){
        User user=new User("tintin","232323");
        accountMapper.createNewUser(user);
        Assert.assertEquals(user.getName(),accountMapper.getUserByName("tintin").getName());
        Assert.assertEquals(user.getPassword(),accountMapper.getUserByName("tintin").getPassword());
    }

    @Test
    public void selectUserById(){
        User user=new User("tintin","232323");
        accountMapper.createNewUser(user);
        User user1=accountMapper.getUserByName("tintin");
        Assert.assertEquals(user1.getName(),accountMapper.selectUserById(user1.getId()).getName());
        Assert.assertEquals(user1.getPassword(),accountMapper.selectUserById(user1.getId()).getPassword());
    }

    @Test
    public void getAdminByNameTest(){
        Admin admin=new Admin("addmin","232323");
        accountMapper.createNewAdmin(admin);
        Assert.assertEquals(admin.getName(),accountMapper.getAdminByName("addmin").getName());
        Assert.assertEquals(admin.getPassword(),accountMapper.getAdminByName("addmin").getPassword());
    }

    @Test
    public void selectAdminById(){
        Admin admin=new Admin("addmin","23fdsf");
        accountMapper.createNewAdmin(admin);
        Admin admin1=accountMapper.getAdminByName("addmin");
        Assert.assertEquals(admin1.getName(),accountMapper.selectAdminById(admin1.getId()).getName());
        Assert.assertEquals(admin1.getPassword(),accountMapper.selectAdminById(admin1.getId()).getPassword());
    }
}
