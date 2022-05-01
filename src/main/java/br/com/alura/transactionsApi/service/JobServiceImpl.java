package br.com.alura.transactionsApi.service;

import static br.com.alura.transactionsApi.extensions.MassaExecution.DadosExecucao;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.transactionsApi.entity.FileInfo;
import br.com.alura.transactionsApi.exceptions.BusinessException;
import br.com.alura.transactionsApi.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobServiceImpl implements IJobService {

	private JobLauncher jobLauncher;
	private Job job;
	private TransactionRepository transactionRepository;
	private FileInfoServiceImpl fileInfoServiceImpl;

//	@Value("${value.filePath}")
	private final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "inputFile" + File.separator;

	public JobServiceImpl(JobLauncher jobLauncher, Job job, TransactionRepository transactionRepository,
			FileInfoServiceImpl fileInfoServiceImpl) {
		this.jobLauncher = jobLauncher;
		this.job = job;
		this.transactionRepository = transactionRepository;
		this.fileInfoServiceImpl = fileInfoServiceImpl;
	}

	@Override
	public String upload(MultipartFile file, RedirectAttributes attributes) throws Exception {
		DadosExecucao.setFirstDateFromFile(null);
		DadosExecucao.setFlag(true);
		DadosExecucao.setTable(transactionRepository.findAll().isEmpty());

		if (file.isEmpty()) {
			attributes.addFlashAttribute("message", "Please, select a file to upload.");
			return "redirect:/";
		}

		var fileName = StringUtils.cleanPath(file.getOriginalFilename());
		var path = Paths.get(UPLOAD_DIR + fileName);
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		var jobParameters = new JobParametersBuilder()
				.addString("INPUT_FILE_PATH", path.toAbsolutePath().toString())
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();

		var execution = jobLauncher.run(job, jobParameters);
		if (execution.getStatus() == BatchStatus.FAILED) {
			Files.delete(path);
			if(execution.getAllFailureExceptions().get(0).getCause() instanceof BusinessException ) {
				attributes.addFlashAttribute("message", execution.getAllFailureExceptions().get(0).getCause().getMessage());
				return "redirect:/";
			}			
			attributes.addFlashAttribute("message", "Job failed." );
			return "redirect:/";			
		}
		
		var fileInfo = new FileInfo();
		fileInfo.setFileName(fileName);
		this.fileInfoServiceImpl.save(fileInfo);
		log.info("File added successfully.");
		Files.delete(path);
		attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
		return "redirect:/";
	}	
	
	@Override
	public String getFiles(Model model) {
		log.info("Get Files");
		var files = this.fileInfoServiceImpl.getFiles();
		model.addAttribute("fileList", files);
		return "index";
	}

}
