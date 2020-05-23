package com.gl.processcontroller.adapter;

import org.springframework.beans.factory.annotation.Autowired;

import com.gl.processcontroller.dto.Order;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;

public class OrderServiceAdapter implements JobHandler{
	
	@Autowired
	private ZeebeClient zeebeClient;

	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {
		
		final Order order = job.getVariablesAsType(Order.class);
	    System.out.println("new job with orderId: " + order.getOrderId());
	    client.newCompleteCommand(job.getKey()).variables(order).send();
	}

}
