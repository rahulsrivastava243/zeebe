package com.gl.glcart.ShippingService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.glcart.ShippingService.producer.ShippingProducer;

@Service
public class ShippingService {
	
	@Autowired
	ShippingProducer shippingProducer;
	
	public String shipItem(String orderId) {
		        shippingProducer.sendMessage(orderId);
			    return orderId + "shipped successfully.";
	  }
	
}
