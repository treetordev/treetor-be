package com.example.treetor;

import com.example.treetor.service.Brevo;
import com.example.treetor.service.OnboardingTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TreetorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreetorApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(Brevo brevo) {
		return args -> {
			brevo.send(new OnboardingTemplate("Akash","google.com"), "manish.nupt@gmail.com");
		};
	}

}
