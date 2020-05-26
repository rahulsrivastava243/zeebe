package com.gl.glcart.InventoryService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.glcart.InventoryService.model.Inventory;
import com.gl.glcart.InventoryService.service.InventoryService;

@RestController
public class InventoryController {
	@Autowired
	InventoryService InventoryService;
	
	@PostMapping("/inventory")
	public String processInventory(@RequestBody Inventory inventory) {
		return InventoryService.processInventory(inventory.getOrderId());
	}
}
