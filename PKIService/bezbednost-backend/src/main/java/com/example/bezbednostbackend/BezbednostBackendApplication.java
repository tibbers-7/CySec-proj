package com.example.bezbednostbackend;

import com.example.bezbednostbackend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BezbednostBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BezbednostBackendApplication.class, args);
	}

}
