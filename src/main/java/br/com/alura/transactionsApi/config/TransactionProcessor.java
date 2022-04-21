package br.com.alura.transactionsApi.config;

import static br.com.alura.transactionsApi.config.MassaExecution.DadosExecucao;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.item.ItemProcessor;

import br.com.alura.transactionsApi.exceptions.BusinessException;
import br.com.alura.transactionsApi.helpers.ExtractorHelper;
import br.com.alura.transactionsApi.helpers.SupplierHelper;
import br.com.alura.transactionsApi.model.Transaction;
import br.com.alura.transactionsApi.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionProcessor implements ItemProcessor<TransactionFile, Transaction>  {

	private TransactionRepository transactionRepository;
//	private String firstDateFromFile = null;

	public TransactionProcessor(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Transaction process(TransactionFile item) throws Exception {
		extractFirstDate(item);
		
		if (DadosExecucao.isFlag()) {
			if (isTransactionDuplicated(DadosExecucao.getFirstDateFromFile())) {
				throw new BusinessException("Found duplicated records!");				
			}			
		}
			
		if (!skipEmptyFields(item))
			return null;

		String dateFromFile = extractDateFromString(item.getDataHoraTransacao(), "T");

		if (!DadosExecucao.getFirstDateFromFile().equals(dateFromFile))
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
	
	private boolean isTransactionDuplicated(String firstDateFromFile) {
			Optional<Transaction> registers = getRegisters(transactionRepository.findAll(),
					x -> extractDateFromString(x.getDataHoraTransacao(), "T").equals(firstDateFromFile));
			if (registers.isPresent())
				return true;			
		

		return false;		
	}
	
	private boolean skipEmptyFields(TransactionFile item) {
		int counter = 0;		
		try {		

			for (PropertyDescriptor p : getProperties(TransactionFile.class)) {
				if (fields().contains(p.getName()) && !p.getReadMethod().invoke(item).toString().isBlank()) {
					log.info("Column ---> {} | Value ---> {}", p.getName(), p.getReadMethod().invoke(item));
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
		if (DadosExecucao.getFirstDateFromFile() == null) {
			Optional<PropertyDescriptor> first = Stream.of(getProperties(TransactionFile.class))
					.filter(p -> p.getName().equalsIgnoreCase("dataHoraTransacao"))
					.findFirst();
			
			if (first.isPresent()) {
				String str = first.get().getReadMethod().invoke(item).toString();
				DadosExecucao.setFirstDateFromFile(extractDateFromString(str, "T"));
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
	
	private String extractDateFromString(String str, String index) {
		return new ExtractorHelper().extractDateFromString(str, index);		
	}
	
	public <T> Optional<T> getRegisters(List<T> list, Predicate<? super T> value) {
		return new SupplierHelper().getRegisters(list, value);
	}
	
	

}
