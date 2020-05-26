package com.gl.glcart.OrderService.producer;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.gl.glcart.OrderService.domain.Order;

@Service
public class OrderProducer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	private static final String TOPIC = "order_processed";

	@Autowired
	private KafkaTemplate<String, Order> kafkaTemplate;

	public void sendMessage(Order message) {

		logger.info(String.format("$$ -> Producing message --> %s", message));

		this.kafkaTemplate.send(TOPIC, message);

	}

}
