package com.example.online_quizz_ritzy_system.response;

import org.springframework.http.HttpStatus;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseApi<T>{
	
	private HttpStatus status;
	private String message;
	
	private T data ;
		
    public ResponseApi(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
}
