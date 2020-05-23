package io.zeebe;

import java.util.Map;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;
import io.zeebe.client.api.worker.JobWorker;

public class B {

	public static void main(String[] args) {
		final String contactPoint = args.length >= 1 ? args[0] : "127.0.0.1:26500";

	    System.out.println("Connecting to broker: " + contactPoint);

	    final ZeebeClient client =
	        ZeebeClient.newClientBuilder().brokerContactPoint(contactPoint).usePlaintext().build();

	    System.out.println("Connected to broker: " + contactPoint);

	    final DeploymentEvent deployment =
	        client.newDeployCommand().addResourceFromClasspath("order-process.bpmn").send().join();

	    final int version = deployment.getWorkflows().get(0).getVersion();
	    System.out.println("Workflow deployed. Version: " + version);

	    final JobWorker jobWorker =
		        client
		            .newWorker()
		            .jobType("inventory-service")
		            .handler(
		                (jobClient, job) -> {
		                  final Map<String, Object> variables = job.getVariablesAsMap();

		                  System.out.println("Process order for inventory: " + variables.get("orderId"));
		                  System.out.println("Order items:"+variables.get("orderItems"));
		                 

		                  // ...

		                 
		                  jobClient.newCompleteCommand(job.getKey()).send().join();
		                })
		                 .open();

		   
	 

		    System.out.println("Closed inventory.");

	}

}
