package com.gl.processController.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;


public class Order implements Serializable{
	private static final long serialVersionUID = 7021673254914829869L;
private String customerId;
  private String orderId = UUID.randomUUID().toString();
  private String payment_mode;
  
  private ArrayList<Item> items = new ArrayList<>();
  
  
public String getCustomerId() {
	return customerId;
}
public Order(String customerId, String orderId, String payment_mode) {
	super();
	this.customerId = customerId;
	this.orderId = orderId;
	this.payment_mode = payment_mode;
}

public Order() {
	super();
}
public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
public String getOrderId() {
	return orderId;
}
public void setOrderId(String orderId) {
	this.orderId = orderId;
}
public String getPayment_mode() {
	return payment_mode;
}
public void setPayment_mode(String payment_mode) {
	this.payment_mode = payment_mode;
}

  public ArrayList<Item> getItems() { return items; } public void
  setItems(ArrayList<Item> items) { this.items = items; }
 
  
}
