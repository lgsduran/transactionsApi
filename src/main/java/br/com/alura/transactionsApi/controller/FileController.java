package br.com.alura.transactionsApi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.transactionsApi.entity.FileEntity;
import br.com.alura.transactionsApi.service.FileServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class FileController {
	
	private FileServiceImpl fileServiceImpl;
	
	@GetMapping("files")
    public List<FileEntity> getAllFiles() {
        return fileServiceImpl.getAllFiles();
    }

}
