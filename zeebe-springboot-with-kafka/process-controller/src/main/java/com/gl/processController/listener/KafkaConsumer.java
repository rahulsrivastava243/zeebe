package com.gl.processController.listener;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.gl.processController.model.Inventory;
import com.gl.processController.model.Order;
import com.gl.processController.model.Payment;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.client.api.worker.JobWorker;

@Service
public class KafkaConsumer {
	
	
	@Autowired
	ZeebeClient zeebeClient;

    @KafkaListener(topics = "shipping_processed", group = "group_id")
    public void consume(String orderId) {
    	correlateWorkflow(orderId);
    	final JobWorker jobWorker =
		        zeebeClient
		            .newWorker()
		            .jobType("shipped")
		            .handler(
		                (jobClient, job) -> {
		                  final Map<String, Object> variables = job.getVariablesAsMap();
		                  System.out.println("shipped order for order id : " + variables.get("orderId"));
		                  jobClient.newCompleteCommand(job.getKey()).send().join();
		                })
		                 .open();
		    System.out.println("shipped");
    }
    
    private void correlateWorkflow(String corelationID) {
    	zeebeClient.newPublishMessageCommand().messageName("order_received").correlationKey(corelationID).send().join();
		    System.out.println("order_received.");
		
	}


    @KafkaListener(topics = "order_processed", group = "group_json",
            containerFactory = "userKafkaListenerFactory")
    public void consumeJson(Order order) {
        System.out.println("Consumed JSON Message: " + order);
        final Map<String, Object> data = new HashMap<>();
		data.put("orderId", order.getOrderId());
		data.put("items", order.getItems());
		data.put("payment_mode",order.getPayment_mode());
		  final WorkflowInstanceEvent wfInstance =
			       zeebeClient
			            .newCreateInstanceCommand()
			            .bpmnProcessId("glcart")
			            .latestVersion()
			            .variables(data)
			            .send()
			            .join();

			    final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();

			    System.out.println("Workflow instance created. Key: " + workflowInstanceKey);
		 final JobWorker jobWorker =
			        zeebeClient
			            .newWorker()
			            .jobType("initiate-order")
			            .handler(
			                (jobClient, job) -> {
			                  final Map<String, Object> variables = job.getVariablesAsMap();

			                  System.out.println("Process order: " + variables.get("orderId"));
			                  System.out.println("Collect money");

			                  // ...

			                  final Map<String, Object> result = new HashMap<>();
			                  result.put("totalPrice", 46.50);

			                  jobClient.newCompleteCommand(job.getKey()).variables(result).send().join();
			                })
			            .fetchVariables("orderId")
			            .open();
		System.out.println("trackingId: " + order.getOrderId());
	
    }
    
    @KafkaListener(topics = "inventory_processed", group = "group_json_inventory",
            containerFactory = "inventoryKafkaListenerFactory")
    public void sendMessage(Inventory inventory) {
    	zeebeClient.newPublishMessageCommand().messageName("order_received").correlationKey(inventory.getOrderId()).send().join();
		System.out.println("inventory processed for orderId " + inventory.getOrderId());
		final JobWorker jobWorker =
		        zeebeClient
		            .newWorker()
		            .jobType("inventory_calculation")
		            .handler(
		                (jobClient, job) -> {
		                  final Map<String, Object> variables = job.getVariablesAsMap();
		                  System.out.println("Process order for inventory: " + variables.get("orderId"));
		                  jobClient.newCompleteCommand(job.getKey()).send().join();
		                })
		                 .open();
	}
    
    @KafkaListener(topics = "payment_processed", group = "group_json_payment",
            containerFactory = "paymentKafkaListenerFactory")
    public void payment(Payment payment) {
    	zeebeClient.newPublishMessageCommand().messageName("order_received").correlationKey(payment.getOrderId()).send().join();
		System.out.println("inventory processed for orderId " + payment.getOrderId());
		final JobWorker jobWorker =
		        zeebeClient
		            .newWorker()
		            .jobType("payment_rec")
		            .handler(
		                (jobClient, job) -> {
		                  final Map<String, Object> variables = job.getVariablesAsMap();
		                  System.out.println("payment done for " + variables.get("orderId"));
		                  jobClient.newCompleteCommand(job.getKey()).send().join();
		                })
		                 .open();
		    System.out.println("payement done.");
	}
}
