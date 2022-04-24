package br.com.alura.transactionsApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.transactionsApi.service.JobServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class TransactionController {

	private JobServiceImpl jobServiceImpl;

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws Exception {
		return this.jobServiceImpl.upload(file, attributes);
	}
	
	@GetMapping("/files")
	public String getUsers(Model model) {
	   return this.jobServiceImpl.getFiles(model);
	}

	
}
