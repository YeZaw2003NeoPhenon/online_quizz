package com.example.online_quizz_ritzy_system.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.online_quizz_ritzy_system.dto.EntityConverter;
import com.example.online_quizz_ritzy_system.dto.QuestionDto;
import com.example.online_quizz_ritzy_system.dto.QuestionRequest;
import com.example.online_quizz_ritzy_system.dto.QuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.online_quizz_ritzy_system.entity.Question;
import com.example.online_quizz_ritzy_system.exception.QuestionAlreadyExistsException;
import com.example.online_quizz_ritzy_system.exception.QuestionNotFoundException;
import com.example.online_quizz_ritzy_system.repository.QuestionRepository;

@Service
public class QuestionServiceImp implements QuestionService{
	
	private final QuestionRepository questionRepository;

	private final EntityConverter<Question, QuestionRequest> entityConverter;

	private final EntityConverter<Question, QuestionDto> entityConverterDto;

	private final EntityConverter<Question, QuestionResponse> entityConverterResponse;

	@Autowired
	public QuestionServiceImp(QuestionRepository questionRepository, EntityConverter<Question, QuestionRequest> entityConverter, EntityConverter<Question, QuestionDto> entityConverterDto, EntityConverter<Question, QuestionResponse> entityConverterResponse) {
		this.questionRepository = questionRepository;
        this.entityConverter = entityConverter;
        this.entityConverterDto = entityConverterDto;
        this.entityConverterResponse = entityConverterResponse;
    }

    @Override
	public QuestionResponse createQuestion(QuestionRequest request) {

		if( request != null && questionRepository.existsByQuestionAndSubject(request.getQuestion() ,request.getSubject())) {
	        throw new QuestionAlreadyExistsException("A question with the same content already exists for this subject." + request.getSubject());
		}
		Question question = entityConverter.dtoToEntity(request, Question.class);

		Question savedQuestion = questionRepository.save(question);

		return entityConverterResponse.entityToDto(savedQuestion, QuestionResponse.class);
	}

	@Override
	public List<QuestionDto> getAllQuestions(){
		return questionRepository.findAll().stream()
				.map(question -> entityConverterDto.entityToDto(question, QuestionDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public QuestionDto findQuestionById(Long id) {
	    return questionRepository.findById(id)
				.map(question -> entityConverterDto.entityToDto(question, QuestionDto.class))
	            .orElseThrow(() -> new QuestionNotFoundException("Question with ID " + id + " not found"));
	}

	@Override
	public List<String> getAllSubjects() {
		return questionRepository.findDistinctSubject();
	}

	@Override
	public QuestionResponse updateQuestion(Long id, QuestionRequest updateRequest) {

		Question existingQuestion = questionRepository.findById(id)
	            .orElseThrow(() -> new QuestionNotFoundException("Question with ID " + id + " not found"));;
	            
//	            if (existingQuestion.getCorrect_choice() == null || existingQuestion.getCorrect_choice().isEmpty()) {
//	                throw new IllegalArgumentException("Correct choice must not be empty");
//	            }
	            
	            if(updateRequest.getQuestion() != null ) {
	            	existingQuestion.setQuestion(updateRequest.getQuestion());
	            }
	            
	            if(updateRequest.getChoices() != null ) {
	               List<String> updatedChoices = updateRequest.getChoices().stream().filter(Objects::nonNull).collect(Collectors.toList());
	            	existingQuestion.setChoices(updatedChoices);
	            }
	            
	            if(updateRequest.getCorrect_choice() != null ) {
	            List<String> updatedCorrectChoices = updateRequest.getCorrect_choice().stream().filter(Objects::nonNull).collect(Collectors.toList());
	            	existingQuestion.setCorrect_choice(updatedCorrectChoices);
	            }
	            
	            existingQuestion.setUpdated_at(new Timestamp(System.currentTimeMillis()));

				if(updateRequest.getSubject() != null) {
	            	existingQuestion.setSubject(updateRequest.getSubject());
	            }

				Question UpdatedQuestion = questionRepository.save(existingQuestion);

			return entityConverterResponse.entityToDto(UpdatedQuestion, QuestionResponse.class);
	}

	@Override
	public void deleteQuestion(Long id) {
	
		questionRepository.findById(id).ifPresentOrElse(questionRepository::delete , () -> {
			new QuestionNotFoundException("Question with ID " + id + " not ubiquitously traceable to delete");
		});
	}

	@Override
	public List<QuestionDto> getQuestionForUser(Integer numsOfQuestions, String subject) {
		Pageable pageable = PageRequest.of(0, numsOfQuestions);
		if (subject == null || subject.isEmpty()) {
			return questionRepository.findAll(pageable).getContent().stream()
					.map(q -> entityConverterDto.entityToDto(q, QuestionDto.class))
					.collect(Collectors.toList());
		}
		return questionRepository.findAllBySubject(subject, pageable).getContent().stream().map(q ->
						entityConverterDto.entityToDto(q, QuestionDto.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public Page<QuestionDto> getQuestions(String subject, String question, int page, int size, String sortBy,
			String sortDirection) {
		
		Sort.Direction actualDirection = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;


		Pageable pageable = PageRequest.of(page, size, Sort.by(actualDirection, sortBy));


		if(subject != null  && question != null) {
			return questionRepository.findAllBySubjectAndQuestionContaining(subject, question, pageable).map(q ->
				entityConverterDto.entityToDto(q, QuestionDto.class));
		}
		else if(subject != null) {
			return questionRepository.findAllBySubject(subject, pageable).map(q ->
					entityConverterDto.entityToDto(q, QuestionDto.class));
		}
		else if(question != null) {
			return questionRepository.findAllByQuestion(question, pageable).map(q ->
					entityConverterDto.entityToDto(q, QuestionDto.class));
		}
		else {
			return questionRepository.findAll(pageable).map(q ->
					entityConverterDto.entityToDto(q, QuestionDto.class));
		}
	}
	
}
