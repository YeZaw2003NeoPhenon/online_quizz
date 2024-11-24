package com.example.online_quizz_ritzy_system.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class globalConfig {
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
}
