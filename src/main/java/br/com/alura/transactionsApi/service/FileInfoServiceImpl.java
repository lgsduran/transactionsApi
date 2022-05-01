package br.com.alura.transactionsApi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.alura.transactionsApi.entity.FileInfo;
import br.com.alura.transactionsApi.repository.FileInfoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileInfoServiceImpl implements IFileInfoService {
	
	private FileInfoRepository fileInfoRepository;	

	@Override
	public List<FileInfo> getFiles() {
//		return this.fileInfoRepository.findAll();
		return this.fileInfoRepository.findAllByOrderByCreatedAtDesc();
	}

	@Override
	public void save(FileInfo fileInfo) {
		this.fileInfoRepository.save(fileInfo);		
	}

}
