package com.gl.glcart.PaymentService.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.glcart.PaymentService.model.Payment;
import com.gl.glcart.PaymentService.producer.PaymentProducer;

@Service
public class PaymentService {
	@Autowired
	PaymentProducer paymentProducer;

	public String acceptPayment(Payment payment) {
		final String orderId = payment.getOrderId();
		paymentProducer.sendMessage(payment);
		return "payment-received for orderId "+orderId;
	}

}
