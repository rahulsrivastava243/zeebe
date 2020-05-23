package com.gl.processcontroller;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import io.zeebe.client.ZeebeClient;

@Configurable
public class ZeebeClientConfiguration {
	
	@Bean
	public ZeebeClient zeebeClient() {
		return ZeebeClient.newClientBuilder().usePlaintext().build();
	}
	

}
