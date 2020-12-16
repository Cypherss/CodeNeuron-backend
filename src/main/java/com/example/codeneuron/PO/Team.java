package com.example.codeneuron.PO;

import com.example.codeneuron.VO.TeamForm;

public class Team {
    private int id;

    private String name;

    private int leaderId;

    public Team(){}
    public Team(TeamForm teamForm){
        this.name=teamForm.getName();
        this.leaderId=teamForm.getLeaderId();
    }

    public Team(int id,String name,int leaderId){
        this.id=id;
        this.name=name;
        this.leaderId=leaderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.leaderId = leaderId;
    }
}
