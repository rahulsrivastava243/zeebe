package com.gl.processcontroller.listner;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListner {
	
	@KafkaListener(topics = "order_processed", groupId = "foo")
	public void orderProcessed(String message) {
	    System.out.println("Received Messasge in group foo: " + message);
	}
	
	@KafkaListener(topics = "payment_received", groupId = "foo")
	public void paymentReceived(String message) {
	    System.out.println("Received Messasge in group foo: " + message);
	}
	
	@KafkaListener(topics = "inventory_calculated", groupId = "foo")
	public void listen(String message) {
	    System.out.println("Received Messasge in group foo: " + message);
	}

}
