package com.example.codeneuron.Service.Account;

import com.example.codeneuron.VO.*;

public interface AccountService {

    public ResponseVO registerUser(UserForm userForm);

    public ResponseVO registerAdmin(AdminForm adminForm);

    public UserVO loginUser(UserForm userForm);

    public AdminVO loginAdmin(AdminForm adminForm);

    ResponseVO getAllAdmins();

    ResponseVO getAllUsers();

    ResponseVO getUsersNameLike(String name);

}


