package com.example.codeneuron.VO;

import com.example.codeneuron.PO.User;

public class UserVO {
    private Integer id;
    private String name;
    private String password;

    public UserVO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
