package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Admin;
import com.example.codeneuron.PO.User;
import com.example.codeneuron.Service.Account.AccountService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface AccountMapper {

    public int createNewUser(User user);

    public int createNewAdmin(Admin admin);

    public List<User> selectAllUsers();

    User getUserByName(@Param("name") String name);

    User selectUserById(@Param("id") int id);

    List<Admin> selectAllAdmins();

    Admin getAdminByName(@Param("name") String name);

    Admin selectAdminById(@Param("id") int id);

    List<User> getUsersNameLike(@Param("name")String name);
    /**
     * 以上
     */

}
