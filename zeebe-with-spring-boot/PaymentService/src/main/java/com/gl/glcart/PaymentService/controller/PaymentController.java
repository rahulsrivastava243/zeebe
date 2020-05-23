package com.gl.glcart.PaymentService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.glcart.PaymentService.model.Payment;
import com.gl.glcart.PaymentService.service.PaymentService;

@RestController
public class PaymentController {
	@Autowired
	PaymentService paymentService;

	@PostMapping("/payment")
	public String acceptPayment(@RequestBody Payment payment) {
		return paymentService.acceptPayment(payment);
	}
}
