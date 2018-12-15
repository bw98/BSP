package com.BSP.bean;

import java.util.Date;

public class Collection {
    private Date collTime;
    private int userId;
    private int bookId;
    private int id;

    public Date getCollTime() {
        return collTime;
    }

    public void setCollTime(Date collTime) {
        this.collTime = collTime;
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

    @Override
    public String toString() {
        return "Collection{" +
                "collTime=" + collTime +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", id=" + id +
                '}';
    }
}
