package com.gl.glcart.OrderService.domain;

import java.io.Serializable;

public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3677301704272164797L;
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
	
}
