package com.example.codeneuron.VO;

import com.example.codeneuron.PO.Domain;

public class DomainVO {
    private int id;

    private double closenessThreshold;

    private int projectId;

    public DomainVO(Domain domain){
        this.id=domain.getId();
        this.closenessThreshold=domain.getClosenessThreshold();
        this.projectId=domain.getProjectId();
    }

    public int getId(){return id;};

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
