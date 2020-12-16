package com.example.codeneuron.Service.Comment;

import com.example.codeneuron.VO.ResponseVO;

public interface CommentService {
    /**
     * 获取评论
     * @param projectId
     * @param type 有效类型包括 node,edge,domain
     * @return
     */
    public ResponseVO getComment(int projectId,String type);
    public ResponseVO getOneComment(String type,int relatedId);
    /**
     * 增加评论
     * @param title
     * @param content
     * @param type 有效类型同上
     * @return
     */
    public ResponseVO addComment(String title,String content,int userId,String userName,String type, int relatedId);

    /**
     * 更新评论
     * @param commentId
     * @param title
     * @param content
     * @return
     */
    public ResponseVO updateComment(int commentId,String title,String content);

    /**
     * 删除评论
     * @param commentId
     * @param type 有效类型同上
     * @return
     */
    public ResponseVO deleteComment(int commentId,int relatedId,String type);

   }
