package br.com.alura.transactionsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.transactionsApi.entity.FileInfo;

public interface FileInfoImpl extends JpaRepository<FileInfo, Long> {

}
