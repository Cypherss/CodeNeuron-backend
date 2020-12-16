package com.example.codeneuron.PO;

/**
 * 邻接表或逆邻接表上的节点
 * 由代表方法的node节点和紧密度构成
 * 紧密度初始值设为0
 */
public class GraphNode {
    private Node node;
    private double closeness;
    public GraphNode(Node node){
        this.node=node;
        this.closeness=0.0;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setCloseness(double closeness) {
        this.closeness = closeness;
    }

    public Node getNode() {
        return node;
    }

    public double getCloseness() {
        return closeness;
    }
}
