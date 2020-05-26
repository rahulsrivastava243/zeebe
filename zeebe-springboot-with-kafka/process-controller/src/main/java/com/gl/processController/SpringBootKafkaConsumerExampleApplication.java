package com.gl.processController;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;

@SpringBootApplication
public class SpringBootKafkaConsumerExampleApplication {
	
	@Value("${kafka.topics}")
	String[] topics;
	
	@Autowired
	public ZeebeClient zeebeClient;
	public final String workflow = "glcart";

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaConsumerExampleApplication.class, args);
	}
	
	@PostConstruct
	public void deployWorlFlowToZeebe() {
		try {
           
			final DeploymentEvent deployment = zeebeClient.newDeployCommand()
					.addResourceFromClasspath(workflow + ".bpmn").send().join();
			final int version = deployment.getWorkflows().get(0).getVersion();
			System.out.println("Workflow deployed. Version: " + version);
			System.out.println("topics: " + topics);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
