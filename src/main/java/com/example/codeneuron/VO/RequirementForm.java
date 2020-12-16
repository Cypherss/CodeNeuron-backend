package com.example.codeneuron.VO;

public class RequirementForm {
    public RequirementForm(){}
    public RequirementForm(String name,String description,int projectId,int nodeId){
        this.name=name;
        this.description=description;
        this.projectId=projectId;
        this.nodeId=nodeId;
    }
    //需求名
    private String name;
    //需求描述
    private String description;
    private int projectId;
    private int nodeId;

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

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getNodeId() {
        return nodeId;
    }
}
