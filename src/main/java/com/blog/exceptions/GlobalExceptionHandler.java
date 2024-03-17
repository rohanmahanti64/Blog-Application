package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);	
	}
	
	// this method is to handle exceptions coming for the validation -->
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException
	(MethodArgumentNotValidException ex){
		Map<String, String> validationMessage = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error ->{
			String field = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			
			validationMessage.put(field, message);
		});
		
		return new ResponseEntity<Map<String,String>>(validationMessage, HttpStatus.BAD_REQUEST );
	}
	
	@ExceptionHandler(WrongCredentialsException.class)
	public ResponseEntity<ApiResponse> handleWrongCredentialsException(WrongCredentialsException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

}
