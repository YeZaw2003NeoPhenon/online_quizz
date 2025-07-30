package com.example.online_quizz_ritzy_system.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Question already exists")
public class QuestionAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public QuestionAlreadyExistsException(String message) {
		super(message);
	}

}
