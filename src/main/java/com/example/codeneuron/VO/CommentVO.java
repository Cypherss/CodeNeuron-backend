package com.example.codeneuron.VO;

import com.example.codeneuron.PO.Comment;

import java.sql.Timestamp;

public class CommentVO {
    private int id;
    private String title;
    private String content;
    private int userId;
    private String userName;
    private Timestamp createTime;
    private String type;
    private int relatedId;

    public CommentVO(){}

    public CommentVO(Comment comment){
        this.id=comment.getId();
        this.title=comment.getTitle();
        this.content=comment.getContent();
        this.userId =comment.getUserId();
        this.userName = comment.getUserName();
        this.createTime = comment.getCreateTime();
        this.type=comment.getType();
        this.relatedId=comment.getRelatedId();
    }


    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(int relatedId) {
        this.relatedId = relatedId;
    }
}
