package br.com.alura.transactionsApi.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class Response {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String code;
	private String message;

	public Response(String message) {
		this.message = message;
	}
	
	public Response(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
