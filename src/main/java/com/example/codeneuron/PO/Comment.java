package com.example.codeneuron.PO;

import java.sql.Time;
import java.sql.Timestamp;

public class Comment {
    private int id;
    private String title;
    private String content;
    private int userId;
    private String userName;
    private Timestamp createTime;
    private String type;
    private int relatedId; //对应的边、点、连通域的id

    public Comment(int id, String title, String content, int userId, String userName, Timestamp createTime, String type, int relatedId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createTime = createTime;
        this.type = type;
        this.relatedId = relatedId;
    }
    public Comment(){}
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(int relatedId) {
        this.relatedId = relatedId;
    }
}
