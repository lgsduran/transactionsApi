package br.com.alura.transactionsApi.config;

import java.lang.reflect.Field;

import org.springframework.batch.item.ItemProcessor;

import br.com.alura.transactionsApi.model.Transaction;

public class TransactionProcessor implements ItemProcessor<TransactionFile, Transaction>  {

	@Override
	public Transaction process(TransactionFile item) throws Exception {
		Field[] fields = item.getClass().getFields();
		for (Field field : fields) {
			System.out.println("+++++"+field);
		}
		System.out.println("------------------------");
		Transaction transaction = new Transaction();
		transaction.setBancoOrigem(item.getBancoOrigem());
		transaction.setAgenciaOrigem(item.getAgenciaOrigem());
		System.out.println("----> "+item.getAgenciaOrigem());
		transaction.setContaOrigem(item.getContaOrigem());
		transaction.setBancoDestino(item.getBancoDestino());
		transaction.setAgenciaDestino(item.getAgenciaDestino());
		System.out.println("----> "+item.getAgenciaDestino());
		transaction.setContaDestino(item.getContaDestino());
		transaction.setValorTransacao(item.getValorTransacao());
		System.out.println("----> "+item.getDataHoraTransacao());
		transaction.setDataHoraTransacao(item.getDataHoraTransacao());
		System.out.println("------------------------");
		return transaction;
	}
    
}
