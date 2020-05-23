package io.zeebe;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.client.api.worker.JobWorker;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DecisonMaking {

  public static void main(String[] args) {
    final String contactPoint = args.length >= 1 ? args[0] : "127.0.0.1:26500";

    System.out.println("Connecting to broker: " + contactPoint);

    final ZeebeClient client =
        ZeebeClient.newClientBuilder().brokerContactPoint(contactPoint).usePlaintext().build();

    System.out.println("Connected to broker: " + contactPoint);

    final DeploymentEvent deployment =
        client.newDeployCommand().addResourceFromClasspath("order-process1.bpmn").send().join();

    final int version = deployment.getWorkflows().get(0).getVersion();
    System.out.println("Workflow deployed. Version: " + version);

    final Map<String, Object> data = new HashMap<>();
    data.put("orderId", 1200);
    data.put("orderValue", 99);

    final WorkflowInstanceEvent wfInstance =
        client
            .newCreateInstanceCommand()
            .bpmnProcessId("order-process1")
            .latestVersion()
            .variables(data)
            .send()
            .join();

    final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();

    System.out.println("Workflow instance created. Key: " + workflowInstanceKey);

    final JobWorker jobWorker =
        client
            .newWorker()
            .jobType("initiate-payment")
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

    

    payment_received(client);
    ship_without_insurance(client);
    System.out.println("Closed.");
  }

  private static void payment_received(ZeebeClient client){
	  client.newPublishMessageCommand().messageName("payment-received").correlationKey("1200").send().join();
	  		    System.out.println("payment-received.");

	  
  }
  private static void ship_without_insurance(ZeebeClient client) {
	
	  final JobWorker jobWorker =
		        client
		            .newWorker()
		            .jobType("ship-without-insurance")
		            .handler(
		                (jobClient, job) -> {
		                  final Map<String, Object> variables = job.getVariablesAsMap();

		                  System.out.println("Process order for inventory: " + variables.get("orderId"));
		                  System.out.println("Order value:"+variables.get("orderValue"));
		                 

		                  // ...

		                 
		                  jobClient.newCompleteCommand(job.getKey()).send().join();
		                })
		                 .open();

		   
	 

		    System.out.println("Closed inventory.");
  }
  
}
