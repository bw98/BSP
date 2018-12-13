package com.BSP.bean;

import java.util.Date;

public class Comment {

    private String content;
    private Date createTime;
    private int userId;
    private int bookId;
    private int id;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", createTime=" + createTime +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", id=" + id +
                '}';
    }
}
