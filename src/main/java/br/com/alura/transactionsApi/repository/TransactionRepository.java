package br.com.alura.transactionsApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.transactionsApi.entity.FileEntity;
import br.com.alura.transactionsApi.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	List<Transaction> findByDataHoraTransacao(String dataHoraTransacao);

	void save(FileEntity fileEntity);
	

}
