package com.example.codeneuron.PO;

import com.example.codeneuron.VO.ProjectForm;
import com.example.codeneuron.VO.ProjectVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Project {
    private int id;

    private String name;

    private double closenessThreshold;//上次操作的连通域？

    private int teamId;

    public Project(ProjectForm projectForm){
        this.setName(projectForm.getName());
        this.setTeamId(projectForm.getTeamId());
        this.setClosenessThreshold(projectForm.getClosenessThreshold());
    }
    public Project(int id, String name, double closenessThreshold, int teamId){
        this.setId(id);
        this.setName(name);
        this.setClosenessThreshold(closenessThreshold);
        this.setTeamId(teamId);
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

    public double getClosenessThreshold() {
        return closenessThreshold;
    }

    public void setClosenessThreshold(double closenessThreshold) {
        this.closenessThreshold = closenessThreshold;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public static Node getNodeByName(String name, List<Node> nodes){
        for(Node n:nodes){
            if(n.getName().equals(name)){
                return n;
            }
        }
        return null;
    }
}
