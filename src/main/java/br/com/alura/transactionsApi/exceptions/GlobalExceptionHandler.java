package br.com.alura.transactionsApi.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.alura.transactionsApi.response.Response;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex) {
		Response response = new Response("422", ex.getMessage());
		log.error("UNPROCESSABLE_ENTITY: {}", ex.getMessage());
		return new ResponseEntity<>(response, UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Response response = new Response("404", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		log.error("Bad request: {}", ex.getMessage());
		return new ResponseEntity<>(response, BAD_REQUEST);
	}
	
//	@ExceptionHandler(UnexpectedTypeException.class)
//	public ResponseEntity<?> handleUnexpectedTypeException(UnexpectedTypeException ex) {
//		Response response = new Response("404", ex.getMessage());
//		log.error("Bad request: {}", ex.getMessage());
//		return new ResponseEntity<>(response, BAD_REQUEST);
//	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobleExcpetionHandler(Exception ex) {		
		if (ex.getMessage().contains("br.com.alura.budgetManagement.enums")) {
			Response response = new Response("404","Invalid input.");
			log.error("Bad request: {}", ex.getMessage());
			return new ResponseEntity<>(response, BAD_REQUEST);
		}
		
		Response response = new Response("500",ex.getMessage());
		log.error("Internal server error: {}", ex.getMessage());
		return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
	}

}
