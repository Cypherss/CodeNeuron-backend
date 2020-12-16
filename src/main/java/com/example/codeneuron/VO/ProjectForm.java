package com.example.codeneuron.VO;

public class ProjectForm {
    private String name;

    private int teamId;


    private double closenessThreshold;

    public ProjectForm(){}
    public ProjectForm(String name, int teamId, double closenessThreshold){
        this.name = name;
        this.teamId=teamId;
        this.closenessThreshold=closenessThreshold;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public double getClosenessThreshold() {
        return closenessThreshold;
    }

    public void setClosenessThreshold(double closenessThreshold) {
        this.closenessThreshold = closenessThreshold;
    }
}
