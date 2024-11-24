package com.example.online_quizz_ritzy_system.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.online_quizz_ritzy_system.entity.Question;
import com.example.online_quizz_ritzy_system.exception.QuestionAlreadyExistsException;
import com.example.online_quizz_ritzy_system.exception.QuestionNotFoundException;
import com.example.online_quizz_ritzy_system.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService{
	
	private final QuestionRepository questionRepository;
	
	@Override
	public Question createQuestion(Question question) {
		
		if( question != null && questionRepository.existsByQuestionAndSubject(question.getQuestion(),question.getSubject())) {
	        throw new QuestionAlreadyExistsException("A question with the same content already exists for this subject." + question.getSubject());
		}
		
		return questionRepository.save(question);
	}

	@Override
	public List<Question> getAllQuestions(){
		return questionRepository.findAll();
	}

	@Override
	public Question findQuestionById(Long id) {
	    return questionRepository.findById(id)
	            .orElseThrow(() -> new QuestionNotFoundException("Question with ID " + id + " not found"));
	}

	@Override
	public List<String> getAllSubjects() {
		return questionRepository.findDistinctSubject();
	}
	
	
	@Override
	public Question updateQuestion(Long id, Question targetedQuestion) {
		Question existingQuestion = questionRepository.findById(id)
	            .orElseThrow(() -> new QuestionNotFoundException("Question with ID " + id + " not found"));;
	            
//	            if (existingQuestion.getCorrect_choice() == null || existingQuestion.getCorrect_choice().isEmpty()) {
//	                throw new IllegalArgumentException("Correct choice must not be empty");
//	            }
	            
	            if(targetedQuestion.getQuestion() != null ) {
	            	existingQuestion.setQuestion(targetedQuestion.getQuestion());
	            }
	            
	            if(targetedQuestion.getChoices() != null ) {
	            List<String> updatedChoices = targetedQuestion.getChoices().stream().filter(Objects::nonNull).collect(Collectors.toList());
	            	existingQuestion.setChoices(updatedChoices);
	            }
	            
	            if(targetedQuestion.getCorrect_choice() != null ) {
	            List<String> updatedCorrectChoices = targetedQuestion.getCorrect_choice().stream().filter(Objects::nonNull).collect(Collectors.toList());
	            	existingQuestion.setCorrect_choice(updatedCorrectChoices);
	            }    
	            
	            existingQuestion.setUpdated_at(new Timestamp(System.currentTimeMillis()));
	            
		  return questionRepository.save(existingQuestion);
	}

	@Override
	public void deleteQuestion(Long id) {
	
		questionRepository.findById(id).ifPresentOrElse(questionRepository::delete , () -> {
			new QuestionNotFoundException("Question with ID " + id + " not ubiquitously traceable to delete");
		});
	}

	@Override
	public List<Question> getQuestionForUser(Integer numsOfQuestions, String subject) {
		Pageable pageable = PageRequest.of(0, numsOfQuestions);
		
		return questionRepository.findBySubject(subject, pageable).getContent(); 
	}
	
	
	@Override
	public Page<Question> getQuestions(String subject, String question, int page, int size, String sortBy,
			String sortDirection) {
		
		Sort.Direction actualDirection = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(actualDirection, sortBy));
		
//		String sortBy2 = pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getProperty() : "id";
//		String sortDirection2 = pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getDirection().name() : "ASC";
		
		if(subject != null  && question != null) {
			return questionRepository.findAllBySubjectAndQuestionContaining(subject, question, pageable);
		}
		else if(subject != null) {
			return questionRepository.findBySubject(subject, pageable);
		}
		else if(question != null) {
			return questionRepository.findAllByQuestionContaining(question, pageable);
		}
		else {
			return questionRepository.findAll(pageable);
		}
	}
	
}
