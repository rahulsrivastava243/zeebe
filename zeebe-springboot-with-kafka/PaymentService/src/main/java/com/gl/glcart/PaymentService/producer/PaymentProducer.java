package com.gl.glcart.PaymentService.producer;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.gl.glcart.PaymentService.model.Payment;

@Service
public class PaymentProducer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	private static final String TOPIC = "payment_processed";

	@Autowired
	private KafkaTemplate<String, Payment> kafkaTemplate;

	public void sendMessage(Payment message) {

		logger.info(String.format("$$ -> Producing message --> %s", message));

		this.kafkaTemplate.send(TOPIC, message);

	}

}
