package br.com.alura.transactionsApi.config;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.item.ItemProcessor;

import br.com.alura.transactionsApi.model.Transaction;

public class TransactionProcessor implements ItemProcessor<TransactionFile, Transaction>  {

	@Override
	public Transaction process(TransactionFile item) throws Exception {
		
		if (skipEmptyFields(item)) {
			Transaction transaction = new Transaction();
			transaction.setBancoOrigem(item.getBancoOrigem());
			transaction.setAgenciaOrigem(item.getAgenciaOrigem());
			transaction.setContaOrigem(item.getContaOrigem());
			transaction.setBancoDestino(item.getBancoDestino());
			transaction.setAgenciaDestino(item.getAgenciaDestino());
			transaction.setContaDestino(item.getContaDestino());
			transaction.setValorTransacao(item.getValorTransacao());
			transaction.setDataHoraTransacao(item.getDataHoraTransacao());
			return transaction;			
		}
		
		return null;
		
	}
	
	private boolean skipEmptyFields(TransactionFile item) {
		List<String> itemList = Stream.of("agenciaDestino", "agenciaOrigem", "bancoDestino", "bancoOrigem",
				"contaDestino", "contaOrigem", "dataHoraTransacao", "valorTransacao").collect(Collectors.toList());

		int i = 0;

		PropertyDescriptor[] propDescArr;
		try {
			propDescArr = Introspector.getBeanInfo(TransactionFile.class).getPropertyDescriptors();

			for (PropertyDescriptor p : propDescArr) {
				if (itemList.contains(p.getName()) && !p.getReadMethod().invoke(item).toString().isBlank()) {
					System.out.println("+++++" + p.getName() + "---" + p.getReadMethod().invoke(item));
					i++;
				}
			}

			if (itemList.size() == i) {
				return true;
			}

		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		return false;
	}

}
