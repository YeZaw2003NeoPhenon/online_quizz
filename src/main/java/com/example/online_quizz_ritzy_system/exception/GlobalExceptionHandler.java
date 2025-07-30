package com.example.online_quizz_ritzy_system.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(QuestionAlreadyExistsException.class)
	public ResponseEntity<Object> handleQuestionAlreadyExistsException(QuestionAlreadyExistsException exception , HttpServletRequest request){
        ErrorDetailsAPI errorDetails = ErrorDetailsAPI.builder()
                .message(exception.getMessage())
                .details(request.getRequestURI())
                .build();
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, HttpServletRequest request) {

        ErrorDetailsAPI errorDetails = ErrorDetailsAPI.builder()
                .message("An unexpected error occurred: " + ex.getMessage())
                .details(request.getRequestURI())
                .build();
       return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ErrorDetailsAPI> handleQuestionNotFoundException(QuestionNotFoundException ex, HttpServletRequest request) {
        ErrorDetailsAPI errorDetails = ErrorDetailsAPI.builder()
                .message(ex.getMessage())
                .details(request.getRequestURI())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);  // 404 Not Found
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetailsAPI> handleValidationException(MethodArgumentNotValidException exception , HttpServletRequest request){

        Map<String,String> errorsMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorsMap.put(error.getField(), error.getDefaultMessage());
        });

        ErrorDetailsAPI errorDetails = ErrorDetailsAPI.builder()
                .message("Validation failed")
                .details(request.getRequestURI())
                .data(errorsMap)
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}
