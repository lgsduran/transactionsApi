package br.com.alura.transactionsApi.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class DataExecution {
	
	private String firstDateFromFile;
	private boolean flag;

}
