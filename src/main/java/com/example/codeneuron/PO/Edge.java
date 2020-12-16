package com.example.codeneuron.PO;

public class Edge {
    public Edge(){}
    public Edge(int id,String callerName,String calleeName,String typeOfCall,int projectId){
        this.id=id;
        this.callerName=callerName;
        this.calleeName=calleeName;
        this.typeOfCall=typeOfCall;
        this.projectId=projectId;
    }
    private int id;
    //name包括包名、类名、函数名和参数类型
    private String callerName;
    //name包括包名、类名、函数名和参数类型
    private String calleeName;
    //调用类型，包括M,I,O,S,D
    private String typeOfCall;
    //
    private int projectId;

    private double closeness;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCallerName() {
        return callerName;
    }

    public String getCalleeName() {
        return calleeName;
    }

    public void setCalleeName(String calleeName) {
        this.calleeName = calleeName;
    }

    public String getTypeOfCall() {
        return typeOfCall;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public void setTypeOfCall(String typeOfCall) {
        this.typeOfCall = typeOfCall;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public double getCloseness() {
        return closeness;
    }

    public void setCloseness(double closeness) {
        this.closeness = closeness;
    }
}
