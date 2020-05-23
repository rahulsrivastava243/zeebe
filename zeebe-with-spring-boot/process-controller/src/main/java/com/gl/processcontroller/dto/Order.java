package com.gl.processcontroller.dto;

import java.util.List;
import java.util.UUID;

public class Order {
	
    private int id; 
	private String orderId = UUID.randomUUID().toString();
	private List<Item> items;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	

}
