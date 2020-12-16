package com.example.codeneuron.PO;

public class Node_code {

    private int nodeId;
    private String code;
    public Node_code(){}
    public Node_code(int nodeId, String code){
        this.nodeId=nodeId;
        this.code=code;
    }
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
