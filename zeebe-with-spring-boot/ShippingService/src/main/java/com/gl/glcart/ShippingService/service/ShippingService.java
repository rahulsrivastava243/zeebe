package com.gl.glcart.ShippingService.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.worker.JobWorker;

@Service
public class ShippingService {
	
	@Autowired
	ZeebeClient zeebeClient;
	
	public String shipItem(String orderId) {
		correlateWorkflow(orderId);
		 JobWorker jobWorker =
				 zeebeClient
			            .newWorker()
			            .jobType("shipped")
			            .handler(
			                (jobClient, job) -> {
			                  final Map<String, Object> variables = job.getVariablesAsMap();

			                  System.out.println("Process order for inventory: " + variables.get("orderId"));
			                  System.out.println("Order value:"+variables.get("orderValue"));
			                  jobClient.newCompleteCommand(job.getKey()).send().join();
			                })
			                 .open();

			    System.out.println("shipped");
			    return orderId + "shipped successfully.";
	  }
	
	private void correlateWorkflow(String corelationID) {
		zeebeClient.newPublishMessageCommand().messageName("order_received").correlationKey(corelationID).send().join();
		    System.out.println("order_received.");
		
	}

	

}
