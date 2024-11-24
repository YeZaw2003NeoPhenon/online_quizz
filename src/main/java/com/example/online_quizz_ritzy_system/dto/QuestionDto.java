package com.example.online_quizz_ritzy_system.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuestionDto {

		private String question;
		
		private String subject;
		
	    private String questionType; 
	    
		
		private List<String>choices;
		
	   private List<String>correct_choice;
		
}
