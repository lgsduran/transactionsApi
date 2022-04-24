package br.com.alura.transactionsApi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.transactionsApi.entity.FileInfo;
import br.com.alura.transactionsApi.service.FileInfoServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class FileController {
	
	private FileInfoServiceImpl fileServiceImpl;
	
	@GetMapping("files1")
    public List<FileInfo> getFiles() {
        return fileServiceImpl.getFiles();
    }

}
