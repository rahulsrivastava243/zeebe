package com.gl.glcart.InventoryService;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.worker.JobWorker;

@SpringBootApplication
public class InventoryServiceApplication {
	
	@Autowired
	ZeebeClient zeebeClient;
	
	@PostConstruct
	public void paymentWorker() {
		final JobWorker jobWorker = zeebeClient.newWorker().jobType("inventory_calculation").handler((jobClient, job) -> {
			Map<String,Object> variables = job.getVariablesAsMap();
			String orderId = (String) variables.get("orderId");
			System.out.println("inventory processed for  id "+orderId);
		}).open();

	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
