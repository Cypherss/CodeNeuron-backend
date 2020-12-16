package com.example.codeneuron.PO;

public class Requirement {
    public Requirement(){}
    public Requirement(int id,String name,String description,int projectId){
        this.id=id;
        this.name=name;
        this.description=description;
        this.projectId=projectId;
    }
    private int id;
    //需求名
    private String name;
    //需求描述
    private String description;
    private int projectId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
