package com.example.online_quizz_ritzy_system.repository;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.online_quizz_ritzy_system.entity.Question;


@DataJpaTest
public class QuestionRepositoryTest {
	
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@BeforeEach
	void setup() {
        questionRepository.save(new Question(null, "What is 2+2?", "Math", "Multiple Choice", List.of("2", "3", "4", "5"), List.of("4"), null, null));
        questionRepository.save(new Question(null, "What is the capital of France?", "Geography", "Multiple Choice", List.of("Paris", "Berlin", "Madrid", "Rome"), List.of("Paris"), null, null));
        questionRepository.save(new Question(null, "What is 3+5?", "Math", "Multiple Choice", List.of("6", "7", "8", "9"), List.of("8"), null, null));
        questionRepository.save(new Question(null, "What is the largest ocean?", "Geography", "Multiple Choice", List.of("Atlantic", "Indian", "Pacific", "Arctic"), List.of("Pacific"), null, null));
	    questionRepository.save(new Question(null, "What is H2O?", "Science", "Multiple Choice", List.of("Water", "Oxygen", "Hydrogen", "Salt"), List.of("Water"), null, null));
     }
	
	@Test
	@DisplayName("Should Contains Specific subjects")
	void testOne() {
	List<String> distinctSubjects  = questionRepository.findDistinctSubject();
	assertThat(distinctSubjects).as("it can't be empty").isNotEmpty();
	assertThat(distinctSubjects).containsExactlyInAnyOrder("Math","Geography","Science");
	}
	
	@Test
	@DisplayName("Should find all questions based on subjects and questions")
	void testTwo() {
		
		    Pageable pageable = PageRequest.of(0, 3);
		
	        Page<Question> page = questionRepository.findAllBySubjectAndQuestionContaining("Math", "3", pageable);

	        assertThat(page).isNotEmpty();

	        assertThat(page.getContent()).hasSize(1);

	        // make sure questions we extract are matched up with filtered critera
	        Question question = page.getContent().get(0);
	        assertThat(question.getSubject()).isEqualTo("Math");
	        assertThat(question.getQuestion()).contains("3");
	    
	}
	
	@Test
	@DisplayName("Should Exist by question and subject")
	void testThree() {
		boolean result	= questionRepository.existsByQuestionAndSubject("What is H2O?", "Science");
		assertThat(result).isNotNull();
		assertThat(result).isTrue();
	}
	
	@AfterEach
	void tearDown() {
		questionRepository.deleteAll();
	}
	
	
}
