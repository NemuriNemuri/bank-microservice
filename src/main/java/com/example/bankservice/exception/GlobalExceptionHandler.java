package com.example.bankservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.bankservice.dto.ErrorResponseDto;

import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * handle validation
	 * @param ex
	 * @return error payload
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(
            MethodArgumentNotValidException ex) {

		// get the error message
        String errorMessage = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();
        // get the error response
        ErrorResponseDto error = new ErrorResponseDto(
                400,
                errorMessage
        );
        // return error response
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
	
	/**
	 * handle exception for customer not found
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponseDto> handleNotFound(RuntimeException ex) {

	    if (ex.getMessage().equals("Customer not found")) {
	        return new ResponseEntity<>(
	                new ErrorResponseDto(401, "Customer not found"),
	                HttpStatus.UNAUTHORIZED
	        );
	    }

	    return new ResponseEntity<>(
	            new ErrorResponseDto(400, ex.getMessage()),
	            HttpStatus.BAD_REQUEST
	    );
	}
}
