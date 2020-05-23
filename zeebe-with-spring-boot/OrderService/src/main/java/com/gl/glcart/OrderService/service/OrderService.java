package com.gl.glcart.OrderService.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.glcart.OrderService.domain.Order;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.client.api.worker.JobWorker;

@Service
public class OrderService {

	@Autowired
	ZeebeClient zeebeClient;

	public String placeOrder(Order order) {

		final Map<String, Object> data = new HashMap<>();
		data.put("orderId", order.getOrderId());
		data.put("items", order.getItems());
		data.put("payment_mode",order.getPayment_mode());

		final WorkflowInstanceEvent wfInstance = zeebeClient.newCreateInstanceCommand().bpmnProcessId("glcart")
				.latestVersion().variables(data).send().join();

		final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();

		System.out.println("Workflow instance created. Key: " + workflowInstanceKey);

		final JobWorker jobWorker = zeebeClient.newWorker().jobType("order_service").handler((jobClient, job) -> {
			final Map<String, Object> variables = job.getVariablesAsMap();

			System.out.println("Process order: " + variables.get("orderId"));
			final Map<String, Object> result = new HashMap<>();
			jobClient.newCompleteCommand(job.getKey()).variables(result).send().join();
		}).fetchVariables("orderId").open();
		return "trackingId: " + order.getOrderId();
	
		
	}
	
}
