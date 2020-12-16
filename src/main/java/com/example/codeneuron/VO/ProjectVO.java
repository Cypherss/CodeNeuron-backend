package com.example.codeneuron.VO;

import com.example.codeneuron.PO.Project;
//这个跟form的区别是form是用户新建的时候传到后端的，没有id，插入到数据库后有了id。所以后面再从数据库取出来的是有id的ProjectVO
public class ProjectVO {
    private int id;

    private String name;

    private double closenessThreshold;

    private int teamId;

    public ProjectVO(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.closenessThreshold=project.getClosenessThreshold();
        this.teamId=project.getTeamId();
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
}
