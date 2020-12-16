package com.example.codeneuron.VO;

public class CommentForm {
    private String title;
    private String content;
    private int userId;
    private String userName;
    private String type;
    private int relatedId;

    public CommentForm() {
    }

    public CommentForm(String title, String content, int userId, String userName, String type, int relatedId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.type = type;
        this.relatedId = relatedId;
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
