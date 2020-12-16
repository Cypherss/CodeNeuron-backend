package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Requirement;
import com.example.codeneuron.PO.Requirement_trace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper
public interface Requirement_traceMapper {
    /**
     * 插入需求追踪
     * @param requirement_trace
     * @return
     */
    public int createRequirementTrace(Requirement_trace requirement_trace);

    /**
     * 根据项目和点id查询需求
     * @param projectId
     * @param nodeId
     * @return
     */
    public List<Requirement_trace> selectRequirementsByProjectIdAndNodeId(int projectId,int nodeId);

    /**
     * 根据项目id和需求id查询所有node
     * @param projectId
     * @param requirementId
     * @return
     */
    public List<Requirement_trace> selectRequirementsByProjectIdAndRequirementId(int projectId,int requirementId);

    /**
     * 删除需求追踪
     * @param requirementId
     * @param projectId
     * @param nodeId
     * @return
     */
    public int deleteRequirementTrace(int requirementId,int projectId,int nodeId);
}
