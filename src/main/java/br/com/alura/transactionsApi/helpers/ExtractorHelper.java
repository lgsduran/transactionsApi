package br.com.alura.transactionsApi.helpers;

public class ExtractorHelper {
	
	public String extractDateFromString(String str, String index) {
		return str.substring(0, str.toUpperCase().indexOf(index));		
	}

}
