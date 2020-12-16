package com.example.codeneuron.PO;

public class Domain {
    private int id;

    private double closenessThreshold;

    private int projectId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getClosenessThreshold() {
        return closenessThreshold;
    }

    public void setClosenessThreshold(double closenessThreshold) {
        this.closenessThreshold = closenessThreshold;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
