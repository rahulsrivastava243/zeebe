package com.gl.glcart.PaymentService;

import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;
import io.zeebe.client.api.worker.JobWorker;
import javassist.bytecode.Descriptor.Iterator;

@SpringBootApplication
public class PaymentServiceApplication {
	@Autowired
	ZeebeClient zeebeClient;

	@PostConstruct
	public void paymentWorker() {
		final JobWorker jobWorker = zeebeClient.newWorker().jobType("payment_rec").handler((jobClient, job) -> {
			Map<String,Object> variables = job.getVariablesAsMap();
			String orderId = (String) variables.get("orderId");
			System.out.println("payment recived for id "+orderId);
		}).open();

	}

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}
}
