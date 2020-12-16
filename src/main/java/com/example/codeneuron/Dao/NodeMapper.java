package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Domain;
import com.example.codeneuron.PO.Node;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Mapper
@Service
public interface NodeMapper {
    /**
     * 创建新节点
     * @param node
     * @return
     */
    public int createNewNode(Node node);

    /**
     * 删除节点
     * @param id
     * @return
     */
    public int deleteNode(int id);

    /**
     * 根据名字删除节点
     * @param name
     * @return
     */
    public int deleteNodeByName(String name);

    /**
     * 选择所有节点
     * @return
     */
    public List<Node> selectAllNodes();

    /**
     * 根据id选择节点
     * @param id
     * @return
     */
    public Node selectNodeById(int id);

    /**
     * 根据名字选择节点
     * @param name
     * @return
     */
    public Node selectNodeByName(String name);
    public Node selectNodeByNameAndProjectId(String name, int projectId);

    /**
     * 选择project对应的所有顶点
     * @param projectId
     * @return
     */
    public List<Node> selectNodeByProjectId(int projectId);

    /**
     * 选择连通域对应的所有顶点
     * @param domainId
     * @return
     */
    public List<Node> selectNodeByDomain(int domainId);

    /**
     * 为project插入多条边
     * @param nodeList
     * @param projectId
     * @return
     */
    public int insertNodeForProject(@Param("list")List<Node> nodeList, int projectId);

    /**
     * 为连通域插入多条边
     * @param nodeList
     * @param domainId
     * @return
     */
    public int insertNodeForDomain(@Param("list")List<Node> nodeList,@Param("domainId") int domainId);

    /**
     * 为project删除全部顶点
     * @param projectId
     * @return
     */
    public int deleteNodeForProject(int projectId);

    /**
     * 为domain删除全部顶点
     * @param domainId
     * @return
     */
    public int deleteNodeForDomain(int domainId);

    public int deleteNodeForDomains(@Param("list")List<Domain> domainList);

    public int updateNodeX(double x,int nodeId,int projectId);

    public int updateNodeY(double y,int nodeId,int projectId);

    public double getNodeX(int nodeId,int projectId);

    public double getNodeY(int nodeId,int projectId);

}
