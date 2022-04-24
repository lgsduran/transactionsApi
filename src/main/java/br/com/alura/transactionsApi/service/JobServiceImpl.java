package br.com.alura.transactionsApi.service;

import static br.com.alura.transactionsApi.extensions.MassaExecution.DadosExecucao;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.transactionsApi.entity.FileEntity;
import br.com.alura.transactionsApi.exceptions.BusinessException;
import br.com.alura.transactionsApi.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobServiceImpl implements IJobService {

	private JobLauncher jobLauncher;
	private Job job;
	private TransactionRepository transactionRepository;

//	@Value("${value.filePath}")
	private String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "inputFile" + File.separator;

	public JobServiceImpl(JobLauncher jobLauncher, Job job, TransactionRepository transactionRepository) {
		this.jobLauncher = jobLauncher;
		this.job = job;
		this.transactionRepository = transactionRepository;
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

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		Path path = Paths.get(UPLOAD_DIR + fileName);
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		JobParameters jobParameters = new JobParametersBuilder()
				.addString("INPUT_FILE_PATH", path.toAbsolutePath().toString())
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();

		JobExecution execution = jobLauncher.run(job, jobParameters);
		if (execution.getStatus() == BatchStatus.FAILED)
			throw new BusinessException("Job " + execution.getJobId() + " is " + execution.getStatus());
		
		addFile(fileName);
		log.info("File added successfully.");

		attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
		return "redirect:/";
	}

	private void addFile(String fileName) {
		FileEntity fileEntity = new FileEntity(fileName);
		transactionRepository.save(fileEntity);
	}

}
