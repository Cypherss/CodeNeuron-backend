package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface CommentMapper {
    /**
     * @param projectId
     * @return
     */
    public List<Comment> getCommentOfNodesForProject(int projectId);

    public List<Comment> getCommentOfEdgesForProject(int projectId);

    public List<Comment> getCommentOfDomainsForProject(int projectId);

    public List<Comment> getCommentOfNodeByNodeId(int nodeId);

    public List<Comment> getCommentOfEdgeByEdgeId(int edgeId);

    public List<Comment> getCommentOfDomainByDomainId(int domainId);

    public int createComment(Comment comment);

    public int createCommentForNode(@Param("nodeId")int nodeId, @Param("commentId")int commentId);

    public int createCommentForEdge(@Param("edgeId")int edgeId, @Param("commentId")int commentId);

    public int createCommentForDomain(@Param("domainId")int domainId, @Param("commentId")int commentId);

    public int deleteComment(int commentId);

    public int deleteCommentForEdge(@Param("commentId")int commentId, @Param("edgeId")int edgeId);

    public int deleteCommentForNode(@Param("commentId")int commentId, @Param("nodeId")int nodeId);

    public int deleteCommentForDomain(@Param("commentId")int commentId, @Param("domainId")int domainId);

    public int updateComment(Comment comment);
}
