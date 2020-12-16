package com.example.codeneuron.PO;

import javax.swing.text.TabExpander;

public class TeamUser {
    private int teamId;

    private int userId;
    public TeamUser(){}
    public TeamUser(int teamId,int userId){
        this.teamId=teamId;
        this.userId=userId;
    }
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
