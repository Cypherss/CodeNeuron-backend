package com.example.codeneuron.PO;

public class Requirement_trace {
    public Requirement_trace(){}
    public Requirement_trace(int requirementId,int projectId,int nodeId){
        this.requirementId=requirementId;
        this.projectId=projectId;
        this.nodeId=nodeId;
    }
    private int requirementId;
    private int projectId;
    private int nodeId;

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getRequirementId() {
        return requirementId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public void setRequirementId(int requirementId) {
        this.requirementId = requirementId;
    }
}
