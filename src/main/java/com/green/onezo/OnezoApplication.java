package com.green.onezo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(servers = {@Server(url = "https://13fa-118-45-167-118.ngrok-free.app/")})
public class OnezoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnezoApplication.class, args);
	}
  
}
