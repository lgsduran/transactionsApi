package br.com.alura.transactionsApi.config.listeners;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobResultListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("Job {} {} at {}.", jobExecution.getJobId(), BatchStatus.STARTED, jobExecution.getStartTime());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
//		if (jobExecution.getStatus() == BatchStatus.COMPLETED )		
		log.info("Job {} is {} at {}.", jobExecution.getJobId(), jobExecution.getStatus(), jobExecution.getEndTime());

	}

}
