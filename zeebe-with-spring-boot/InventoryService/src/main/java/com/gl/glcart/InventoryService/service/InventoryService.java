package com.gl.glcart.InventoryService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.zeebe.client.ZeebeClient;

@Service
public class InventoryService {
	@Autowired
	ZeebeClient zeebeClient;

	public String processInventory(String orderId) {
		return sendMessage(orderId);
	}

	public String sendMessage(String orderId) {
		zeebeClient.newPublishMessageCommand().messageName("payment_received").correlationKey(orderId).send().join();
		return "inventory processed for orderId " + orderId;
	}
}
