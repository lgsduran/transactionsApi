package br.com.alura.transactionsApi.config;

import org.springframework.batch.core.ItemReadListener;

public class StepItemReadListener implements ItemReadListener<TransactionFile> {

	@Override
	public void beforeRead() {
		System.out.println("ItemReadListener - beforeRead");
	}

	@Override
	public void afterRead(TransactionFile item) {
		System.out.println("ItemReadListener - afterRead");
	}

	@Override
	public void onReadError(Exception ex) {
		System.out.println("ItemReadListener - onReadError");
	}

}
