package com.example.codeneuron.Controller.Account;

import com.example.codeneuron.ServiceImpl.Account.AccountServiceImpl;
import com.example.codeneuron.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class AccountController {

    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";

    @Autowired
    private AccountServiceImpl accountService;

    @PostMapping("/loginUser")
    public ResponseVO loginUser(@RequestBody UserForm userForm){
        UserVO user = accountService.loginUser(userForm);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }
    @PostMapping("/loginAdmin")
    public ResponseVO loginAdmin(@RequestBody AdminForm adminForm){
        AdminVO admin = accountService.loginAdmin(adminForm);
        if(admin==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(admin);
    }

    @PostMapping("/registerUser")
    public ResponseVO registerUser(@RequestBody UserForm userForm){
        return accountService.registerUser(userForm);
    }

    @PostMapping("/registerAdmin")
    public ResponseVO registerAdmin(@RequestBody AdminForm adminForm){
        return accountService.registerAdmin(adminForm);
    }

    @GetMapping("/allUser")
    public ResponseVO getAllUser(){
            return accountService.getAllUsers();
    }

    @GetMapping("/allAdmin")
    public ResponseVO getAllAdmin(){
        return accountService.getAllAdmins();
    }

    @GetMapping("/namelike")
    public ResponseVO getUsersNameLike(@RequestParam("name")String name){ return accountService.getUsersNameLike(name);}
}
