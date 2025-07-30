package com.example.online_quizz_ritzy_system.service;

import java.util.List;

import com.example.online_quizz_ritzy_system.dto.QuestionDto;
import com.example.online_quizz_ritzy_system.dto.QuestionRequest;
import com.example.online_quizz_ritzy_system.dto.QuestionResponse;
import org.springframework.data.domain.Page;

import com.example.online_quizz_ritzy_system.entity.Question;


public interface QuestionService {

	QuestionResponse createQuestion(QuestionRequest question);
	
	 List<QuestionDto> getAllQuestions();
	
	 QuestionDto findQuestionById(Long id);
	
	 List<String> getAllSubjects();
	
	 void deleteQuestion(Long id);

	QuestionResponse updateQuestion(Long id , QuestionRequest question);
	
	 List<QuestionDto> getQuestionForUser(Integer numsOfQuestions , String subject);

	 Page<QuestionDto> getQuestions(String subject , String question, int page , int size , String sortBy , String sortDirection);

}
