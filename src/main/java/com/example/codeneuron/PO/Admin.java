package com.example.codeneuron.PO;

public class Admin {

    private Integer id;

    private String name;

    private String password;

    private int role;

    public Admin(){
        this.role=2;
    }
    public Admin(String name,String password){this.name=name;this.password=password;this.role=2;};

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
