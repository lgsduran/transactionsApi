package br.com.alura.transactionsApi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.alura.transactionsApi.entity.FileEntity;
import br.com.alura.transactionsApi.repository.FileRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileServiceImpl implements IFileService {
	
	private FileRepository fileRepository;	

	@Override
	public List<FileEntity> getAllFiles() {
		return fileRepository.findAll();
	}

}
