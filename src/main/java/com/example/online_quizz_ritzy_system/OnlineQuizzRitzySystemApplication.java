package com.example.online_quizz_ritzy_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class OnlineQuizzRitzySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineQuizzRitzySystemApplication.class, args);
	}
}