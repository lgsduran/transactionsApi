package br.com.alura.transactionsApi.config;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

public class StepItemWriteListener implements ItemWriteListener<TransactionFile> {

	@Override
	public void beforeWrite(List<? extends TransactionFile> items) {
		System.out.println("ItemWriteListener - beforeWrite");
		
	}

	@Override
	public void afterWrite(List<? extends TransactionFile> items) {
		System.out.println("ItemWriteListener - afterWrite");
		
	}

	@Override
	public void onWriteError(Exception exception, List<? extends TransactionFile> items) {
		System.out.println("ItemWriteListener - onWriteError");
		
	}

}
