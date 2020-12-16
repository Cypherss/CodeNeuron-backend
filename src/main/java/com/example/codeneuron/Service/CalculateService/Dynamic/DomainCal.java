package com.example.codeneuron.Service.CalculateService.Dynamic;

import com.example.codeneuron.VO.ResponseVO;

public interface DomainCal {
    /**
     * 连通域个数计算//有点复杂，不用这个
     * @return
     */
    public ResponseVO ConnectedDomainCount(double threshold, int projectId);

    /**
     * 如果需要统计domain数量，用这个，没有计算domain，直接读取domain数据库
     * @param threshold
     * @param projectId
     * @return
     */
    public ResponseVO getDomainCountByThresholdAndProjectId(double threshold, int projectId);
    /**
     * 获取连通域
     * @return
     */
    public ResponseVO TopologyCalculate(double threshold, int projectId);

    /**
     * 给前端用的设置阈值、更新连通域的方法，会把domain和domain_node写入数据库
     * @param threshold
     * @return
     */
    public ResponseVO setThreshold(double threshold, int projectId);

    /**
     * 给前端用的获取当前project的连通域和其中的顶点的方法
     * @param projectId
     * @return 返回的是HashMap<Domain, List<Node>>
     */
    public ResponseVO getDomains(int projectId);
}
