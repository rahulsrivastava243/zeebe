package io.zeebe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.client.api.worker.JobWorker;

public class A {
	
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

	    final Map<String, Object> data = new HashMap<>();
	    data.put("orderId", 50001);
	    data.put("orderItems", Arrays.asList(100, 200, 300));

	    final WorkflowInstanceEvent wfInstance =
	        client
	            .newCreateInstanceCommand()
	            .bpmnProcessId("order-process")
	            .latestVersion()
	            .variables(data)
	            .send()
	            .join();

	    final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();

	    System.out.println("Workflow instance created. Key: " + workflowInstanceKey);

	    final JobWorker jobWorker =
	        client
	            .newWorker()
	            .jobType("payment-service")
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
	}

}
