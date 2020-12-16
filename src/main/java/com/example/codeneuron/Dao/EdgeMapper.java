package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Edge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface EdgeMapper {
    /**
     * 创建新调用关系
     * @param edge
     * @return
     */
    public int createNewEdge(Edge edge);

    /**
     * 删除调用关系
     * @param id
     * @return
     */
    public int deleteEdge(int id);

    /**
     * 选择所有调用关系
     * @return
     */
    public List<Edge> selectAllEdges();

    /**
     * 通过id选择调用关系
     * @param id
     * @return
     */
    public Edge selectEdgeById(int id);

    /**
     * 通过调用者名和被调用者名选择调用关系
     * @param callerName
     * @param calleeName
     * @return
     */
    public Edge selectEdgeByCallerAndCallee(String callerName, String calleeName);

    /**
     * 通过projectId选择所有边
     * @param projectId
     * @return
     */
    public List<Edge> selectEdgeByProjectId(int projectId);

    /**
     * 更新同一project中多条边的紧密度
     * @param edgeList
     * @return
     */
    public int updateEdgeCloseness(@Param("list")List<Edge> edgeList);

    /**
     * 为project插入多条边
     * @param edgeList
     * @param projectId
     * @return
     */
    public int insertEdgeForProject(@Param("list")List<Edge> edgeList,@Param("projectId") int projectId);

    /**
     * 将project中的所有边删除
     * @param projectId
     * @return
     */
    public int deleteEdgeForProject(int projectId);
}
