package com.example.online_quizz_ritzy_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public class QuestionDto {

		private Long id;

		@NotBlank
		private String question;

		@NotBlank
		private String subject;

		@NotBlank
	    private String questionType; 

		@NotNull
		private List<String>choices;

		@NotNull
	    private List<String>correct_choice;

	public QuestionDto(){}

	public QuestionDto(Long id, String question, String subject, String questionType, List<String> choices, List<String> correct_choice) {
		this.id = id;
		this.question = question;
		this.subject = subject;
		this.questionType = questionType;
		this.choices = choices;
		this.correct_choice = correct_choice;
	}

	public QuestionDto(String question, String subject, String questionType, List<String> choices, List<String> correct_choice) {
		this.question = question;
		this.subject = subject;
		this.questionType = questionType;
		this.choices = choices;
		this.correct_choice = correct_choice;
	}

	public QuestionDto(String question, String subject, String questionType) {
		this.question = question;
		this.subject = subject;
		this.questionType = questionType;
	}

	public String getQuestion() {
		return question;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

	public List<String> getCorrect_choice() {
		return correct_choice;
	}

	public void setCorrect_choice(List<String> correct_choice) {
		this.correct_choice = correct_choice;
	}
}
