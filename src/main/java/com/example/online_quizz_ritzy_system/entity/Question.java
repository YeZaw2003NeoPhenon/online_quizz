package com.example.online_quizz_ritzy_system.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @Entity
  @Table
  public class Question{
	  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
    @NotBlank
	private String question;
	   
	@Column(nullable = false)
	@NotBlank
	private String subject;
	
    @Column(nullable = false)
    @NotBlank
    private String questionType; 
    
	@ElementCollection
	@CollectionTable(name = "choices", joinColumns = @JoinColumn(name = "question_id"))
	@Column(name = "choice")
	private List<String> choices = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name = "correct_choices", joinColumns = @JoinColumn(name = "question_id"))
	@Column(name = "correct_answer")
	private List<String>correct_choice = new ArrayList<>();
	
	@CreationTimestamp
	@Column(nullable = false , updatable = false)
	private Timestamp createdAt;	
	
	@UpdateTimestamp
    @Column(nullable = false)
	private Timestamp updated_at;
	
}
  