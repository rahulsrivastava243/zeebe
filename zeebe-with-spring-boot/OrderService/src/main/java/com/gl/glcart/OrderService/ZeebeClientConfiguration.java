package com.gl.glcart.OrderService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.zeebe.client.ZeebeClient;

@Configuration
public class ZeebeClientConfiguration {
	@Bean
	public ZeebeClient zeebe() {
		ZeebeClient client = ZeebeClient.newClientBuilder().usePlaintext().build();
		return client;
	}
}
