package com.example.codeneuron.VO;

import com.example.codeneuron.PO.Team;

import java.util.List;

public class TeamVO {
    private int id;

    private String name;

    private int leaderId;

    //private List<Integer> teamUsers;

    public TeamVO(Team team){
        this.id=team.getId();
        this.leaderId=team.getLeaderId();
        this.name=team.getName();
        //this.teamUsers=team.getTeamUsers();
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

    //public List<Integer> getTeamUsers() {
       // return teamUsers;
    //}

    //public void setTeamUsers(List<Integer> teamUsers) {
        //this.teamUsers=teamUsers;
    //}
}
