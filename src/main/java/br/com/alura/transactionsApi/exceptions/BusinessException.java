package br.com.alura.transactionsApi.exceptions;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = UNPROCESSABLE_ENTITY)
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BusinessException() {}

	public BusinessException(String message) {
		super(message);
	}

}
