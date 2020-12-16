package com.example.codeneuron.VO;

import com.example.codeneuron.PO.Admin;

public class AdminVO {
    private Integer id;
    private String name;
    private String password;

    public AdminVO(Admin admin){
        this.id = admin.getId();
        this.name = admin.getName();
        this.password = admin.getPassword();
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
