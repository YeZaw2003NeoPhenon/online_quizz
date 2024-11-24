package com.example.online_quizz_ritzy_system.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.online_quizz_ritzy_system.dto.EntityConverter;
import com.example.online_quizz_ritzy_system.dto.QuestionDto;
import com.example.online_quizz_ritzy_system.entity.Question;
import com.example.online_quizz_ritzy_system.response.FeedbackMessage;
import com.example.online_quizz_ritzy_system.response.ResponseApi;
import com.example.online_quizz_ritzy_system.service.QuestionServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
	
	private final QuestionServiceImp questionServiceImp;
	
	private final EntityConverter<Question , QuestionDto> entityConverter;
	
    @RequestMapping(value = "/create-new-question",method = RequestMethod.POST)
	public ResponseEntity<ResponseApi<QuestionDto>> createQuestion(@Valid @RequestBody Question question){
		
		Question createdQuestion = questionServiceImp.createQuestion(question);
		
		QuestionDto questionDto = entityConverter.entityToDto(createdQuestion, QuestionDto.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi<QuestionDto>(HttpStatus.OK, FeedbackMessage.question_creation_success_message, questionDto));
	
   }
	
    @RequestMapping(value = "/all-questions",method = RequestMethod.GET)
    public ResponseEntity<ResponseApi<List<Question>>> getAllQuestions(){
    	
        List<Question> questions = questionServiceImp.getAllQuestions();
        
		  if (questions.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NO_CONTENT)
		                .body(new ResponseApi<>(HttpStatus.NO_CONTENT, "No questions found", null));
		    }
		  
		    return ResponseEntity.status(HttpStatus.OK)
		            .body(new ResponseApi<>(HttpStatus.OK, FeedbackMessage.Questions, questions));	
    }
    
    @RequestMapping(value = "/question/{id}",method = RequestMethod.GET)
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id){
        Question theQuestion = questionServiceImp.findQuestionById(id);
        
        QuestionDto questionDto = entityConverter.entityToDto(theQuestion, QuestionDto.class);
        
        return ResponseEntity.ok(questionDto);
    }
    
    @RequestMapping(value = "/question/{id}/update",method = RequestMethod.PUT)
    public ResponseEntity<ResponseApi<QuestionDto>> updateQuestion(
            @PathVariable Long id, @RequestBody Question question) {
    	
        Question theQuestion = questionServiceImp.updateQuestion(id, question);
        
        QuestionDto updatedQuestion = entityConverter.entityToDto(theQuestion, QuestionDto.class);
        
	    return ResponseEntity.status(HttpStatus.OK)
	            .body(new ResponseApi<>(HttpStatus.OK, FeedbackMessage.question_update_success_message, updatedQuestion));	
    }
    
   
    @RequestMapping(value = "/question/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseApi<QuestionDto>> deleteQuestion(@PathVariable Long id){
        questionServiceImp.deleteQuestion(id);
		 return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseApi<QuestionDto>(HttpStatus.OK, FeedbackMessage.question_deletion_success_message , null));
    }
    
    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    public ResponseEntity<ResponseApi<List<String>>> getAllSubjects() {
        List<String> subjects = questionServiceImp.getAllSubjects();
        
        if (subjects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ResponseApi<>(HttpStatus.NO_CONTENT, "No subjects found", null));
        }
        
        return ResponseEntity.ok(new ResponseApi<>(HttpStatus.OK, "Subjects fetched successfully", subjects));
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResponseApi<Page<Question>>> getQuestions(
    													@RequestParam(value = "subject" , required = false) String subject,
    													@RequestParam(value = "question" , required = false) String question,
    													@RequestParam(value = "page" , defaultValue = "0")int page,
    													@RequestParam(value = "size" , defaultValue = "10")int size,
    													@RequestParam(value = "sortBy" , defaultValue = "createdAt") String sortBy,
    													@RequestParam(value = "sortDirection" , defaultValue = "asc")String sortDirection){
    	
    	Page<Question> questionsPage = questionServiceImp.getQuestions(subject, question, page, size, sortBy, sortDirection);
    	
		 if( questionsPage.getContent().isEmpty()) {
			 return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseApi<Page<Question>>(HttpStatus.NO_CONTENT,"No questions found", null));
		 }
		    return ResponseEntity.status(HttpStatus.OK)
		            .body(new ResponseApi<>(HttpStatus.OK, FeedbackMessage.Questions, questionsPage));	 
    }
    
    
    @RequestMapping(value = "/quiz/fetch-questions-for-user",method = RequestMethod.GET)
    public ResponseEntity<ResponseApi<List<Question>>> getQuestionForUser(
    		@RequestParam Integer numsOfQuestions , @RequestParam String subject
    		){
    	// render out all questions based on subject
     List<Question> allQuestions = questionServiceImp.getQuestionForUser(numsOfQuestions, subject);
     
     // Handle case when there are no questions available for the given subject
     if (allQuestions.isEmpty()) {
         return ResponseEntity.status(HttpStatus.NO_CONTENT)
                 .body(new ResponseApi<>(HttpStatus.NO_CONTENT, FeedbackMessage.unavaliableMessage, null));
     }
     
     List<Question> mullableQuestions = new ArrayList<>(allQuestions);
     
     // shuffle off the list of questions for random purpose
     Collections.shuffle(mullableQuestions);
     
     int avaliableQuestions = Math.min(numsOfQuestions, mullableQuestions.size());
     
     List<Question> randomQuestions = mullableQuestions.subList(0, avaliableQuestions);
     
     return ResponseEntity.ok(new ResponseApi<>(HttpStatus.OK, FeedbackMessage.confirmed_message, randomQuestions));   
     
    }
    
}