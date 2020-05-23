package com.gl.glcart.OrderService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;

@SpringBootApplication
public class OrderServiceApplication {

	@Autowired
	public ZeebeClient zeebeClient;
	public final String workflow = "glcart";

	// The workflow deployment code has been commented as there is an issue in bpmn
	// diagram .

	@PostConstruct
	public void deployWorlFlowToZeebe() {
		try {

			final DeploymentEvent deployment = zeebeClient.newDeployCommand()
					.addResourceFromClasspath(workflow + ".bpmn").send().join();
			final int version = deployment.getWorkflows().get(0).getVersion();
			System.out.println("Workflow deployed. Version: " + version);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
