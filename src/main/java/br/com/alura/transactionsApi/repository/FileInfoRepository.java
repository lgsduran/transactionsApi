package br.com.alura.transactionsApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.transactionsApi.entity.FileInfo;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

	List<FileInfo> findAllByOrderByCreatedAtDesc();
	
}
