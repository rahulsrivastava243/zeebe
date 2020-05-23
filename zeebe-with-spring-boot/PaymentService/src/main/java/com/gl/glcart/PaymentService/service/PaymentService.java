package com.gl.glcart.PaymentService.service;



import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.glcart.PaymentService.model.Payment;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.worker.JobWorker;
import io.zeebe.client.impl.ZeebeCallCredentials;

@Service
public class PaymentService {
	@Autowired
	ZeebeClient zeebeClient;

	public String acceptPayment(Payment payment) {
		final String orderId = payment.getOrderId();
		return sendMessage(orderId);
	}

	public String sendMessage(String orderId) {
		zeebeClient.newPublishMessageCommand().messageName("payment_received").correlationKey(orderId).send().join();
		return "payment-received for orderId "+orderId;
	}
}
