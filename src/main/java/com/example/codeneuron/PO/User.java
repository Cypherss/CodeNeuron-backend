package com.example.codeneuron.PO;

public class User {
    private Integer id;
    private String name;
    private String password;
    private int role;

    public User(){
        this.role=1;
    }
    public User(String name,String password){this.name=name;this.password=password;this.role=1;};

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
