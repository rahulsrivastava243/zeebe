package com.gl.glcart.OrderService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.glcart.OrderService.domain.Order;
import com.gl.glcart.OrderService.producer.OrderProducer;

@Service
public class OrderService {

	@Autowired
	OrderProducer orderProducer;

	public String placeOrder(Order order) {
		orderProducer.sendMessage(order);
		return "trackingId: " + order.getOrderId();
	}
	
}
