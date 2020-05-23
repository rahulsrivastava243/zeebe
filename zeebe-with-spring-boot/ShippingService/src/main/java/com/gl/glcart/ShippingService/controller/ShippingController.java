package com.gl.glcart.ShippingService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gl.glcart.ShippingService.service.ShippingService;

public class ShippingController {
	@Autowired
	ShippingService shippingService;
	
	@GetMapping("/ship/{orderId}")
	public String placeOder(@PathVariable("orderId")String orderId) {
		return shippingService.shipItem(orderId);
	}

}
