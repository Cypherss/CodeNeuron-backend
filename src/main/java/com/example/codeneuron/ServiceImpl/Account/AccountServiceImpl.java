package com.example.codeneuron.ServiceImpl.Account;

import com.example.codeneuron.Dao.AccountMapper;
import com.example.codeneuron.PO.Admin;
import com.example.codeneuron.PO.User;
import com.example.codeneuron.Service.Account.AccountService;
import com.example.codeneuron.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST="账号已存在";
    private final static String ACCOUNT_NOTEXIST="该账号不存在";

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerUser(UserForm userForm){
        try{
            int flag=0;
            List<User> users=accountMapper.selectAllUsers();
            for(int i=0;i<users.size();i++){
                if(userForm.getName().equals(users.get(i).getName())){
                    flag=1;
                    break;
                }
            }
            if(flag==1){
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }else{
                User user=new User();
                user.setName(userForm.getName());
                user.setPassword(userForm.getPassword());
                accountMapper.createNewUser(user);
            }
        }catch (Exception e){
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO registerAdmin(AdminForm adminForm){
        try{
            int flag=0;
            List<Admin> admins=accountMapper.selectAllAdmins();
            for(int i=0;i<admins.size();i++){
                if(adminForm.getName().equals(admins.get(i).getName())){
                    flag=1;
                    break;
                }
            }
            if(flag==1){
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }else{
                Admin admin=new Admin();
                admin.setName(adminForm.getName());
                admin.setPassword(adminForm.getPassword());
                accountMapper.createNewAdmin(admin);
            }
        }catch (Exception e){
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO loginUser(UserForm userForm){
        User user=accountMapper.getUserByName(userForm.getName());
        if(user==null||!user.getPassword().equals(userForm.getPassword())){
            return null;
        }
        return new UserVO(user);
    }

    @Override
    public AdminVO loginAdmin(AdminForm adminForm){
        Admin admin=accountMapper.getAdminByName(adminForm.getName());
        if(admin==null||!admin.getPassword().equals(adminForm.getPassword())){
            return null;
        }
        return new AdminVO(admin);
    }

    @Override
    public ResponseVO getAllAdmins(){
        try{
            List<Admin> admins=accountMapper.selectAllAdmins();
            return ResponseVO.buildSuccess(admins);
        }catch (Exception e){
            return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
        }
    }

    @Override
    public ResponseVO getAllUsers(){
        try{
            List<User> users=accountMapper.selectAllUsers();
            return ResponseVO.buildSuccess(users);
        }catch (Exception e){
            return ResponseVO.buildFailure(ACCOUNT_NOTEXIST);
        }
    }

    @Override
    public ResponseVO getUsersNameLike(String name){
        List<User> users = accountMapper.getUsersNameLike(name+"%");
        if(users.size()!=0){
            return ResponseVO.buildSuccess(users);
        }else{
            return ResponseVO.buildFailure("No users");
        }
    }
}
