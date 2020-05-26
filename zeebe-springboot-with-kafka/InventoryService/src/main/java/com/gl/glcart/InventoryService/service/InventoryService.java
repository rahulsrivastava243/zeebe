package com.gl.glcart.InventoryService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.glcart.InventoryService.model.Inventory;
import com.gl.glcart.InventoryService.model.producer.InventoryProducer;

import io.zeebe.client.ZeebeClient;

@Service
public class InventoryService {
	
	@Autowired
	InventoryProducer inventoryProducer;

	public String processInventory(String orderId) {
		return sendMessage(orderId);
	}

	public String sendMessage(String orderId) {
		inventoryProducer.sendMessage(new Inventory(orderId));
		return "inventory processed for orderId " + orderId;
	}
}
