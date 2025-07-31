package com.example.online_quizz_ritzy_system.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.online_quizz_ritzy_system.dto.QuestionRequest;
import com.example.online_quizz_ritzy_system.dto.QuestionResponse;
import com.example.online_quizz_ritzy_system.response.ResponseAPI;
import com.example.online_quizz_ritzy_system.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.online_quizz_ritzy_system.dto.QuestionDto;
import com.example.online_quizz_ritzy_system.response.FeedbackMessage;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/questions")
@Slf4j
public class QuestionController {

	private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/create-new-question",method = RequestMethod.POST)
    public ResponseEntity<ResponseAPI<QuestionResponse>> createQuestion(@Valid @RequestBody QuestionRequest request) {

		QuestionResponse createdQuestion = questionService.createQuestion(request);

         return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseAPI.<QuestionResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(FeedbackMessage.question_creation_success_message)
                        .data(createdQuestion)
                        .build()
        );

   }
	
    @RequestMapping(value = "/all-questions",method = RequestMethod.GET)
    public ResponseEntity<ResponseAPI<List<QuestionDto>>> getAllQuestions(){
    	
        List<QuestionDto> questions = questionService.getAllQuestions();

		  if (questions.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NO_CONTENT)
		                .body(
                                ResponseAPI.<List<QuestionDto>>builder()
                                        .statusCode(HttpStatus.NO_CONTENT.value())
                                        .message("No questions found")
                                        .data(null).build());
		    }
		  
		    return ResponseEntity.status(HttpStatus.OK)
		            .body(
                            ResponseAPI.<List<QuestionDto>>builder()
                                    .statusCode(HttpStatus.OK.value())
                                    .message(FeedbackMessage.Questions)
                                    .data(questions).build());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<ResponseAPI<QuestionDto>> getQuestionById(@PathVariable Long id){
        QuestionDto questionDto = questionService.findQuestionById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ResponseAPI.<QuestionDto>builder()
                                .statusCode(HttpStatus.OK.value())
                                .message(FeedbackMessage.found_message)
                                .data(questionDto).build());
    }
    
    @RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
    public ResponseEntity<ResponseAPI<QuestionResponse>> updateQuestion(
            @PathVariable Long id, @RequestBody QuestionRequest question) {
    	
        QuestionResponse updatedQuestion = questionService.updateQuestion(id, question);

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(
                        ResponseAPI.<QuestionResponse>builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message(FeedbackMessage.question_update_success_message)
                                .data(updatedQuestion).build()
                        );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseAPI<Void>> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);

		 return ResponseEntity.status(HttpStatus.OK).body(
                 ResponseAPI.<Void>builder()
                         .statusCode(HttpStatus.CREATED.value())
                         .message(FeedbackMessage.question_deletion_success_message)
                         .data(null).build()
                 );
    }

    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    public ResponseEntity<ResponseAPI<List<String>>> getAllSubjects() {
        List<String> subjects = questionService.getAllSubjects();
        
        if (subjects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(
                            ResponseAPI.<List<String>>builder()
                                    .statusCode(HttpStatus.NO_CONTENT.value())
                                    .message(FeedbackMessage.empty_subjects_message)
                                    .data(null).build()
                    );
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ResponseAPI.<List<String>>builder()
                                .statusCode(HttpStatus.OK.value())
                                .message(FeedbackMessage.fetched_subjects_success_message)
                                .data(subjects).build()
                );
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResponseAPI<Page<QuestionDto>>> getQuestions(
    													@RequestParam(value = "subject" , required = false) String subject,
    													@RequestParam(value = "question" , required = false) String question,
    													@RequestParam(value = "page" , defaultValue = "0")int page,
    													@RequestParam(value = "size" , defaultValue = "10")int size,
    													@RequestParam(value = "sortBy" , defaultValue = "createdAt")String sortBy,
    													@RequestParam(value = "sortDirection" , defaultValue = "asc")String sortDirection){
    	
    	Page<QuestionDto> questionsPage = questionService.getQuestions(subject, question, page, size, sortBy, sortDirection);
    	
		 if(questionsPage.getContent().isEmpty()) {
			 return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    ResponseAPI.<Page<QuestionDto>>builder()
                     .statusCode(HttpStatus.OK.value())
                     .message("No questions found")
                     .build()
             );
		 }
		    return ResponseEntity.status(HttpStatus.OK)
		            .body(
                            ResponseAPI.<Page<QuestionDto>>builder()
                                    .statusCode(HttpStatus.OK.value())
                                    .message(FeedbackMessage.Questions)
                                    .data(questionsPage)
                                    .build()
                    );
    }
    
    
    @RequestMapping(value = "/quiz/fetch-questions-for-user",method = RequestMethod.GET)
    public ResponseEntity<ResponseAPI<List<QuestionDto>>> getQuestionForUser(
    		@RequestParam(name = "num_of_qs") Integer numOfQuestions , @RequestParam String subject
    		){

    	// render out all questions based on subject
     List<QuestionDto> allQuestions = questionService.getQuestionForUser(numOfQuestions, subject);
     
     // Handle case when there are no questions available for the given subject
     if (allQuestions.isEmpty()) {
         return ResponseEntity.status(HttpStatus.NO_CONTENT)
                 .body(
                         ResponseAPI.<List<QuestionDto>>builder()
                                 .statusCode(HttpStatus.NO_CONTENT.value())
                                 .message(FeedbackMessage.unavaliableMessage)
                                 .data(List.of())
                                 .build()
                 );
     }
     
     List<QuestionDto> muttableQuestions = new ArrayList<>(allQuestions);
     
     // shuffle off the list of questions for random purpose
     Collections.shuffle(muttableQuestions);
     
     int availableQuestions = Math.min(numOfQuestions, muttableQuestions.size());
     
     List<QuestionDto> randomQuestions = muttableQuestions.subList(0, availableQuestions);

     return ResponseEntity.status(HttpStatus.OK)
             .body(
                     ResponseAPI.<List<QuestionDto>>builder()
                             .statusCode(HttpStatus.OK.value())
                             .message(FeedbackMessage.confirmed_message)
                             .data(randomQuestions)
                             .build()
             );
    }

}