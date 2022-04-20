package br.com.alura.transactionsApi.config;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.item.ItemProcessor;

import br.com.alura.transactionsApi.model.Transaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionProcessor implements ItemProcessor<TransactionFile, Transaction>  {

	private String firstDateFromFile = null;

	@Override
	public Transaction process(TransactionFile item) throws Exception {
		if (!skipEmptyFields(item))
			return null;

		extractFirstDate(item);
		String dateFromFile = item.getDataHoraTransacao().substring(0,
				item.getDataHoraTransacao().toUpperCase().indexOf("T"));

		if (!firstDateFromFile.equals(dateFromFile))
			return null;

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
	
	private boolean skipEmptyFields(TransactionFile item) {
		int counter = 0;		
		try {		

			for (PropertyDescriptor p : getProperties(TransactionFile.class)) {
				if (fields().contains(p.getName()) && !p.getReadMethod().invoke(item).toString().isBlank()) {
					log.info("Column ---> {} Value ---> {}", p.getName(), p.getReadMethod().invoke(item));
					counter++;
				}
			}

			if (fields().size() == counter) {
				return true;
			}

		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	private void extractFirstDate(TransactionFile item) throws Exception {
		if (firstDateFromFile == null) {
			Optional<PropertyDescriptor> first = Stream.of(getProperties(TransactionFile.class))
					.filter(p -> p.getName().equalsIgnoreCase("dataHoraTransacao"))
					.findFirst();
			
			if (first.isPresent()) {
				String str = first.get().getReadMethod().invoke(item).toString();
				firstDateFromFile = str.substring(0, str.toUpperCase().indexOf("T"));
			}			
		}		
	}
	
	private List<String> fields() {
		return Stream.of("agenciaDestino", "agenciaOrigem", "bancoDestino", "bancoOrigem",
				"contaDestino", "contaOrigem", "dataHoraTransacao", "valorTransacao")
				.collect(Collectors.toList());		
	}
	
	private PropertyDescriptor[] getProperties(Class<?> beanClass) throws IntrospectionException {
		return Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
	}

}
