package com.example.online_quizz_ritzy_system.exception;

public class QuestionNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuestionNotFoundException(String message) {
        super(message);
    }
}
