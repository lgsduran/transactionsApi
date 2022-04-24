package br.com.alura.transactionsApi.service;

import java.util.List;

import br.com.alura.transactionsApi.entity.FileEntity;

public interface IFileService {
	
	List<FileEntity> getAllFiles();

}
