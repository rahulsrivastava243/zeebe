package com.gl.glcart.ShippingService.producer;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShippingProducer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	private static final String TOPIC = "shipping_processed";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String orderId) {

		logger.info(String.format("$$ -> Producing message --> %s", orderId));

		this.kafkaTemplate.send(TOPIC, orderId);

	}

}
