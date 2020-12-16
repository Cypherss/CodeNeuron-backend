package com.example.codeneuron.Service.CalculateService.Dynamic;

import com.example.codeneuron.PO.GraphNode;
import com.example.codeneuron.VO.RequirementForm;
import com.example.codeneuron.VO.ResponseVO;

import java.util.HashMap;
import java.util.LinkedList;

public interface GraphCal {
    /**
     * 依赖紧密度计算
     * @return
     */
    public ResponseVO ClosenessCalculate(int projectId, HashMap<String, LinkedList<GraphNode>> graph, HashMap<String, LinkedList<GraphNode>> inverseGraph);

    /**
     * 顶点个数计算
     * @return
     */
    public ResponseVO NodesCount(int projectId);

    /**
     * 边个数计算
     * @return
     */
    public ResponseVO EdgesCount(int projectId);

    public ResponseVO allNodes(int projectId);

    public ResponseVO allEdges(int projectId);

    /**
     * 执行需求追踪计算
     * @param projectId
     * @return
     */
    public ResponseVO requirementTrace(int projectId);

    public ResponseVO addRequirement(RequirementForm requirementForm);

    public ResponseVO deleteRequirement(int requirementId,int projectId,int nodeId);


}
