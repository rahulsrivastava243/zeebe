package com.gl.glcart.ShippingService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gl.glcart.ShippingService.service.ShippingService;

@RestController
public class ShippingController {
	@Autowired
	ShippingService shippingService;
	
	@GetMapping("/ship/{orderId}")
	public String placeOder(@PathVariable String orderId) {
		return shippingService.shipItem(orderId);
	}

}
