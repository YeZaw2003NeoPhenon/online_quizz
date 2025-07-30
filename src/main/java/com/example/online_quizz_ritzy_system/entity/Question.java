package com.example.online_quizz_ritzy_system.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.validation.constraints.NotBlank;

  @Entity
  @Table(name = "question")
  public class Question{
	  
	@Id
	@SequenceGenerator(
			name = "question_sequence",
			sequenceName = "question_sequence")
	@GeneratedValue(generator = "question_sequence", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable = false)
	private String question;


	@Column(nullable = false)
	private String subject;
	
    @Column(nullable = false)
    private String questionType;

	  @ElementCollection(fetch = FetchType.EAGER)
	  @CollectionTable(name = "choices", joinColumns = @JoinColumn(name = "question_id"))
	  @Column(name = "choice")
	  private List<String> choices = new ArrayList<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "correct_choices", joinColumns = @JoinColumn(name = "question_id"))
	@Column(name = "correct_answer")
	private List<String>correct_choice = new ArrayList<>();
	
	@CreationTimestamp
	@Column(nullable = false , updatable = false)
	private Timestamp createdAt;	
	
	@UpdateTimestamp
    @Column(nullable = false)
	private Timestamp updated_at;

	  public Question(Long id, String question, String subject, String questionType, List<String> choices, List<String> correct_choice, Timestamp createdAt, Timestamp updated_at) {
		  this.id = id;
		  this.question = question;
		  this.subject = subject;
		  this.questionType = questionType;
		  this.choices = choices;
		  this.correct_choice = correct_choice;
		  this.createdAt = createdAt;
		  this.updated_at = updated_at;
	  }

	  public Question(){}

	  public Long getId() {
		  return id;
	  }

	  public String getQuestion() {
		  return question;
	  }

	  public String getSubject() {
		  return subject;
	  }

	  public String getQuestionType() {
		  return questionType;
	  }

	  public List<String> getChoices() {
		  return choices;
	  }

	  public List<String> getCorrect_choice() {
		  return correct_choice;
	  }

	  public Timestamp getCreatedAt() {
		  return createdAt;
	  }

	  public Timestamp getUpdated_at() {
		  return updated_at;
	  }

	  public void setId(Long id) {
		  this.id = id;
	  }

	  public void setQuestion(String question) {
		  this.question = question;
	  }

	  public void setSubject(String subject) {
		  this.subject = subject;
	  }

	  public void setQuestionType(String questionType) {
		  this.questionType = questionType;
	  }

	  public void setChoices(List<String> choices) {
		  this.choices = choices;
	  }

	  public void setCorrect_choice(List<String> correct_choice) {
		  this.correct_choice = correct_choice;
	  }

	  public void setCreatedAt(Timestamp createdAt) {
		  this.createdAt = createdAt;
	  }

	  public void setUpdated_at(Timestamp updated_at) {
		  this.updated_at = updated_at;
	  }
  }