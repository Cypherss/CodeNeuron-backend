package com.example.codeneuron.VO;

import java.util.ArrayList;

public class TeamForm {

    private String name;

    private int leaderId;

    public TeamForm(){}
    public TeamForm(String name, int leaderId){
        this.name = name;
        this.leaderId=leaderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId=leaderId;
    }
}
