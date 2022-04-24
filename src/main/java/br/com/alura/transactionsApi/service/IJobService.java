package br.com.alura.transactionsApi.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface IJobService {

	String upload(MultipartFile file, RedirectAttributes attributes) throws Exception;
	
	String getFiles(Model model);

}
