package com.BSP.bean;

import java.util.Date;

public class Reserve {
    private int id;
    private int userId;
    private int bookId;
    private Date beginData;
    private int status;

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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getBeginData() {
        return beginData;
    }

    public void setBeginData(Date beginData) {
        this.beginData = beginData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
