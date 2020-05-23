package com.gl.glcart.OrderService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.glcart.OrderService.domain.Order;
import com.gl.glcart.OrderService.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/order")
	public String placeOder(@RequestBody Order order) {
		return orderService.placeOrder(order);
	}
}
