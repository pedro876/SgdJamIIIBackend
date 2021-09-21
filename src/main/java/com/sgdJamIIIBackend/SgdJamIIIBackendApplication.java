package com.sgdJamIIIBackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SpringBootApplication
//@EnableWebSocket
public class SgdJamIIIBackendApplication implements CommandLineRunner {
	
	 @Autowired
	 ApartmentRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(SgdJamIIIBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("RUNNING");
	}
}
