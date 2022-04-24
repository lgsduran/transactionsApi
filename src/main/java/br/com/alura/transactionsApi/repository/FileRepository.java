package br.com.alura.transactionsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.transactionsApi.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
