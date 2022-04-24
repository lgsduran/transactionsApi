package br.com.alura.transactionsApi.service;

import java.util.List;

import br.com.alura.transactionsApi.entity.FileInfo;

public interface IFileInfoService {
	
	void save(FileInfo fileInfo);
	
	List<FileInfo> getFiles();

}
