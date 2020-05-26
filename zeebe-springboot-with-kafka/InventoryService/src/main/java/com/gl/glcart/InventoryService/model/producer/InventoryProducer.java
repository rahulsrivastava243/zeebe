package com.gl.glcart.InventoryService.model.producer;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.gl.glcart.InventoryService.model.Inventory;

@Service
public class InventoryProducer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	private static final String TOPIC = "inventory_processed";

	@Autowired
	private KafkaTemplate<String, Inventory> kafkaTemplate;

	public void sendMessage(Inventory message) {

		logger.info(String.format("$$ -> Producing message --> %s", message));

		this.kafkaTemplate.send(TOPIC, message);

	}

}
