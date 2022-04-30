package br.com.alura.transactionsApi.service;

import static br.com.alura.transactionsApi.extensions.MassaExecution.DadosExecucao;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
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

	public JobServiceImpl(JobLauncher jobLauncher, Job job, TransactionRepository transactionRepository, FileInfoServiceImpl fileInfoServiceImpl) {
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

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		Path path = Paths.get(UPLOAD_DIR + fileName);
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		JobParameters jobParameters = new JobParametersBuilder()
				.addString("INPUT_FILE_PATH", path.toAbsolutePath().toString())
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();

		JobExecution execution = jobLauncher.run(job, jobParameters);
		if (execution.getStatus() == BatchStatus.FAILED) {
			attributes.addFlashAttribute("message", "Job failed.");
			return "redirect:/";
		}
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(fileName);
		this.fileInfoServiceImpl.save(fileInfo);
		log.info("File added successfully.");

		attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
		return "redirect:/";
	}	
	
	@Override
	public String getFiles(Model model) {
		log.info("Get Files");
		List<FileInfo> files = this.fileInfoServiceImpl.getFiles();
		List<FileInfo> sortedValues = files.stream()
				.sorted(Comparator.comparing(FileInfo::getCreatedAt).reversed())
				.collect(Collectors.toList());
		model.addAttribute("fileList", sortedValues);
		return "index";
	}

}
