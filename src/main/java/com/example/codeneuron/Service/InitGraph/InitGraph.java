package com.example.codeneuron.Service.InitGraph;
import com.example.codeneuron.PO.Edge;
import com.example.codeneuron.PO.GraphNode;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.VO.ResponseVO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public interface InitGraph {
    /**
     * 邻接表和逆邻接表生成
     * @return
     */
    public HashMap<String, LinkedList<GraphNode>> InitAdjacencyTable(List<Node> nodes, List<Edge> edges);

    /**
     *
     * @param nodes
     * @param edges
     * @return
     */
    public HashMap<String, LinkedList<GraphNode>> InitInverseAdjacencyTable(List<Node> nodes, List<Edge> edges);
}
