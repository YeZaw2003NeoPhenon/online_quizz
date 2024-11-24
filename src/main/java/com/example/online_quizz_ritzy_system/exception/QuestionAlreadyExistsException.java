package com.example.online_quizz_ritzy_system.exception;


public class QuestionAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public QuestionAlreadyExistsException(String message) {
		super(message);
	}

}
