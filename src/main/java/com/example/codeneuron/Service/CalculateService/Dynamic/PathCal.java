package com.example.codeneuron.Service.CalculateService.Dynamic;

import com.example.codeneuron.VO.ResponseVO;

public interface PathCal {
    /**
     * 路径查找
     * @return ArrayList<ArrayList<GraphNode>> 所有路径按
     */
    public ResponseVO searchAllPaths(String source, String target, int projectId);


    public ResponseVO searchShortestPath(String source, String target, int projectId);


    /**
     * 同名函数处理
     * @return ArrayList<String> 所有符合的函数全名
     */
    public ResponseVO getFunctionNameLike(String name, int projectId);
}
