package com.BSP.bean;

import java.util.Date;

public class Book {
	private String name;
	private String type;
	private String author;
	private String intro;
	private int status;
	private int id;
	private int userId;
	private String imgUrl;
	private Date finalDay;//上传图书的人想把图书借到何时

	public Date getFinalDay() {
		return finalDay;
	}

	public void setFinalDay(Date finalDay) {
		this.finalDay = finalDay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}