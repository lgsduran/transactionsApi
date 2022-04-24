package br.com.alura.transactionsApi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import br.com.alura.transactionsApi.service.JobServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class TransactionController {

	private JobServiceImpl jobServiceImpl;
//	private HttpServletRequest request;

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws Exception {
		return jobServiceImpl.upload(file, attributes);
	}
	
//	@PostMapping("/upload")
//    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
//        RedirectAttributes addFlashAttribute = redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        return "redirect:/";
//    }
//	
	
}
