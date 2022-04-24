package br.com.alura.transactionsApi.config;

import org.springframework.batch.core.ItemProcessListener;

import br.com.alura.transactionsApi.entity.Transaction;

public class StepItemProcessListener implements ItemProcessListener<TransactionFile, Transaction>  {

	@Override
	public void beforeProcess(TransactionFile item) {
		System.out.println("ItemProcessListener - beforeProcess");
		
	}

	@Override
	public void afterProcess(TransactionFile item, Transaction result) {
		System.out.println("ItemProcessListener - afterProcess");
		
	}

	@Override
	public void onProcessError(TransactionFile item, Exception e) {
		System.out.println("ItemProcessListener - onProcessError");// TODO Auto-generated method stub
		
	}

}

