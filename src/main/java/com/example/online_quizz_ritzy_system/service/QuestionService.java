package com.example.online_quizz_ritzy_system.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.online_quizz_ritzy_system.entity.Question;


public interface QuestionService {
	
	public abstract Question createQuestion(Question question);
	
	public abstract List<Question> getAllQuestions();
	
	public abstract Question findQuestionById(Long id);
	
	public abstract List<String> getAllSubjects();
	
	public abstract void deleteQuestion(Long id);
	
	public abstract Question updateQuestion(Long id , Question question);
	
	public abstract List<Question> getQuestionForUser(Integer numsOfQuestions , String subject);
	
	public abstract Page<Question> getQuestions(String subject , String question, int page , int size , String sortBy , String sortDirection);

}
