package br.com.alura.transactionsApi.helpers;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class SupplierHelper {
	
	public <T> Optional<T> getRegisters(List<T> list, Predicate<? super T> value) {
		return list.stream().filter(value).findFirst();
	}

}
