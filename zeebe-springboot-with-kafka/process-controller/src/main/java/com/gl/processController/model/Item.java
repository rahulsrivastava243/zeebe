package com.gl.processController.model;

import java.io.Serializable;

public class Item implements Serializable{
	
	private static final long serialVersionUID = -1368709318319990168L;
	private String articleId;
	private int amount;
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
