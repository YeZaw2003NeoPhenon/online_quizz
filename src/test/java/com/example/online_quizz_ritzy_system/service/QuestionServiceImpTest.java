package com.example.online_quizz_ritzy_system.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import com.example.online_quizz_ritzy_system.entity.Question;
import com.example.online_quizz_ritzy_system.exception.QuestionAlreadyExistsException;
import com.example.online_quizz_ritzy_system.exception.QuestionNotFoundException;
import com.example.online_quizz_ritzy_system.repository.QuestionRepository;

@SpringJUnitConfig
@SpringBootTest
class QuestionServiceImpTest {
	
//	@Autowired
//	private JUnitSoftAssertions assertions;
	
	@Autowired
	private QuestionServiceImp questionServiceImp;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	private Question question;
	
	@BeforeEach
	void setUp() {
		question = new Question();
		questionServiceImp = new QuestionServiceImp(questionRepository);
//		assertions = new JUnitSoftAssertions();
		
  }
	
	@Test
	@DisplayName("Should be able to create Question")
	void testOne() {
		question = new Question();
		question.setQuestion("What is the formula for area of a square?");
		question.setSubject("Geometry");
		question.setQuestionType("Multiple Choice");
		question.setChoices(List.of("1/2bh" , "lw","IIr^2"));
		question.setCorrect_choice(List.of("lw"));
		
		Question createdQuestion = questionServiceImp.createQuestion(question);
		
		assertThat(createdQuestion).isNotNull();
		assertThat(createdQuestion).isEqualTo(question);
        assertThat(createdQuestion.getQuestion()).isEqualToIgnoringCase("What is the formula for area of a square?");
        assertThat(createdQuestion.getSubject()).isEqualTo("Geometry");
        assertThat(createdQuestion.getCorrect_choice()).isNotEmpty();
        assertThat(createdQuestion.getCorrect_choice()).contains("lw"); 
	}
	
	@Test
	@DisplayName("Should Throw QuestionAlreadyExistsException while repeative question is created")
	void testTwo() {
		
	    question = new Question(null, "What is the boiling point of water?", "Science", "Multiple Choice", List.of("100°C", "0°C", "50°C", "25°C"), List.of("100°C"), null, null);
	    questionRepository.save(question);
	    
	     Question duplicatedQs = new Question(null, "What is the boiling point of water?", "Science", "Multiple Choice",List.of("200°C", "0°C", "100°C", "25°C"), List.of("100°C"), null, null);
                
	    		assertThat(duplicatedQs).isNotNull();
	    		
                assertThatThrownBy(() -> {
                	questionServiceImp.createQuestion(duplicatedQs);
                }).isInstanceOf(QuestionAlreadyExistsException.class).hasMessageContaining("A question with the same content already exists for this subject.");       
	}
	
	@Test
	@DisplayName("Should be able to delete Question by id")
	void testThree() {		
		
	    question = new Question(null, "What is 2+5?", "Math", "Multiple Choice", List.of("2", "3", "4", "7"), List.of("7"), null, null);
	    
	    questionRepository.save(question);
	    
	    Long questionId = question.getId();
	    
	    assertThat(questionRepository.existsById(questionId)).isTrue();
	    
	    questionServiceImp.deleteQuestion(questionId);
	    
	    assertThat(questionRepository.existsById(questionId)).isFalse();  
	 }
	
	@Test
	@DisplayName("Should find Question By Id")
	void testFour() {
			// when 
	    question = new Question(null, "What is the speed of light?", "Physics", "Multiple Choice", 
                			    List.of("3.0 x 10^8 m/s", "1.5 x 10^8 m/s", "2.0 x 10^8 m/s", "1.0 x 10^8 m/s"), 
                			    List.of("3.0 x 10^8 m/s"), null, null);
	    
	    	questionRepository.save(question);
	    	
	    	// then
	    	Long questionId = question.getId();
	    	
	      	Question targetQuestion = questionServiceImp.findQuestionById(questionId);
	      	
	      	//verify
	      	assertThat(targetQuestion).isNotNull();
	      	assertThat(targetQuestion.getId()).isEqualTo(questionId);
	      	assertThat(targetQuestion.getQuestion()).isEqualTo("What is the speed of light?");
	      	assertThat(targetQuestion.getSubject()).isEqualTo("Physics");
	}
	
	@Test
	@DisplayName("Should throw QuestionNotFoundException when deleting non-existing question")
	@Disabled
	void testFive() {		
	
        Long nonExistingId = 1000L;
        
        assertThatThrownBy(() -> questionServiceImp.deleteQuestion(nonExistingId))
        .isInstanceOf(QuestionNotFoundException.class).hasMessageContaining("Question with given id can not be trackable");
	}
	
	@Test
	@DisplayName("Should return a specified number of questions for a given subject ")
	void testSix() {
		// when 
	    questionRepository.save(new Question(null, "What is 2+2?", "Math", "Multiple Choice", List.of("2", "3", "4", "5"), List.of("4"), null, null));
	    questionRepository.save(new Question(null, "What is 3+5?", "Math", "Multiple Choice", List.of("6", "7", "8", "9"), List.of("8"), null, null));
	    questionRepository.save(new Question(null, "What is the capital of France?", "Geography", "Multiple Choice", List.of("Paris", "Berlin", "Madrid", "Rome"), List.of("Paris"), null, null));
	    
	    
	    // then
	    List<Question> mathQuestions = questionServiceImp.getQuestionForUser(2, "Math");
	    
	    
	    // verify
	    assertThat(mathQuestions).isNotEmpty();
	    assertThat(mathQuestions).hasSize(2);
	    
	    
	    assertThat(mathQuestions.get(0).getSubject()).isEqualTo("Math");
	    assertThat(mathQuestions.get(1).getSubject()).isEqualTo("Math");
	}
	
	@Test
	@DisplayName("Should return all distinct subjects")
	void testSeven() {
		// when
	    questionRepository.save(new Question(null, "What is 2+2?", "Math", "Multiple Choice", List.of("2", "3", "4", "5"), List.of("4"), null, null));
	    questionRepository.save(new Question(null, "What is H2O?", "Science", "Multiple Choice", List.of("Water", "Oxygen", "Hydrogen", "Salt"), List.of("Water"), null, null));
	    questionRepository.save(new Question(null, "What is the capital of France?", "Geography", "Multiple Choice", List.of("Paris", "Berlin", "Madrid", "Rome"), List.of("Paris"), null, null));

	    
	    // then
	    List<String> subjects = questionServiceImp.getAllSubjects();
	    
	    // test 
	    assertThat(subjects).isNotEmpty();
	    assertThat(subjects).containsExactlyInAnyOrder("Math","Geography","Science");
	}
	
	@Test
	@DisplayName("Should return all questions with pagination, sorting and filtering applied")
	void finalTest() {
		// when
	    questionRepository.save(new Question(null, "What is the boiling point of water?", "Science", "Multiple Choice", List.of("100°C", "0°C", "50°C", "25°C"), List.of("100°C"), null, null));
	    questionRepository.save(new Question(null, "What is H2O?", "Science", "Multiple Choice", List.of("Water", "Oxygen", "Hydrogen", "Salt"), List.of("Water"), null, null));
	    questionRepository.save(new Question(null, "What is 2+2?", "Math", "Multiple Choice", List.of("2", "3", "4", "5"), List.of("4"), null, null));
	    questionRepository.save(new Question(null, "What is 3x3?", "Math", "Multiple Choice", List.of("6", "9", "12", "15"), List.of("9"), null, null));
	    questionRepository.save(new Question(null, "What is π?", "Math", "Multiple Choice", List.of("3.14", "22/7", "Both", "None"), List.of("Both"), null, null));
	    // then
	    // verify pagination, sorting and subject filtering
	    // test with science filter, sort by id ascending order
	    Page<Question> scienceQuestions = questionServiceImp.getQuestions("Science", null, 0, 2, "question", "desc");
	   
	    
	    // test
	    assertThat(scienceQuestions.getContent()).isNotEmpty();
	    assertThat(scienceQuestions.getContent()).hasSize(2);
	    assertThat(scienceQuestions.getContent().get(0).getSubject()).isEqualTo("Science");
	    assertThat(scienceQuestions.getContent().get(0).getQuestion()).isGreaterThan(scienceQuestions.getContent().get(1).getQuestion()); // desceding
	    
	    
	    // test with question content filter and sort by id in ascending order 
	    Page<Question> contentFilters = questionServiceImp.getQuestions(null, "2+2", 0, 1, "id", "asc");
	    
	    assertThat(contentFilters.getContent()).hasSize(1);
	    assertThat(contentFilters.getContent().get(0).getSubject()).isEqualTo("Math");
	    assertThat(contentFilters.getContent().get(0).getQuestion()).contains("2+2");
	    
	    // test with math filter and sort by question ascending order
	    
	    Page<Question> mathFilters = questionServiceImp.getQuestions("Math", null , 0, 3, "question", "asc");

	    assertThat(mathFilters.getContent()).isNotEmpty();
	    assertThat(mathFilters.getContent()).hasSize(3);
	    assertThat(mathFilters.getContent().get(0).getQuestion()).isLessThan(mathFilters.getContent().get(1).getQuestion());	// ascending
	    assertThat(mathFilters.getContent().get(2).getQuestion()).isGreaterThan(mathFilters.getContent().get(1).getQuestion());
	 }
	
	@AfterEach
	void tearDown() {
		questionRepository.deleteAll();
	}
	
}
