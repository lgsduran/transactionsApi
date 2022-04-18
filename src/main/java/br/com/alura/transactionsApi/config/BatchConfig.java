package br.com.alura.transactionsApi.config;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import br.com.alura.transactionsApi.model.Transaction;
import br.com.alura.transactionsApi.repository.TransactionRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private TransactionRepository transactionRepository;
//	private final String file_path = "/Users/luizduran/eclipse-workspace/alura-workspace/transactionsApi/inputFile/transacoes-2022-01-01.csv";
	private final String file_path = "C:\\Users\\Qintess\\eclipse-workspace\\workspace-alura\\transactionsApi\\inputFile\\transacoes-2022-01-01.csv";

	public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			TransactionRepository transactionRepository) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.transactionRepository = transactionRepository;
	}
	
//	@Value("${database.driver}")
//	private String databaseDriver;
//	@Value("${database.url}")
//	private String databaseUrl;
//	@Value("${database.username}")
//	private String databaseUsername;
//	@Value("${database.password}")
//	private String databasePassword;
	
//	@Bean
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DataSource dataSource() {
//		return DataSourceBuilder
//				.create()
//				.build();
//	}
	
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(databaseDriver);
//		dataSource.setUrl(databaseUrl);
//		dataSource.setUsername(databaseUsername);
//		dataSource.setPassword(databasePassword);
//		return dataSource;
//	}

	@Bean
	public Job chunkJob() {
		return jobBuilderFactory
				.get("Chunk Job")
				.incrementer(new RunIdIncrementer())
				.start(chunkStep())
				.build();
	}

	@Bean
	public Step chunkStep() {
		return stepBuilderFactory.get("chunkStep")
				.<TransactionFile, Transaction>chunk(3)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.faultTolerant()
				.skip(Throwable.class)
				.skipLimit(Integer.MAX_VALUE)			
				.build();
	}
	
	@Bean
	public FlatFileItemReader<TransactionFile> reader() {
        FlatFileItemReader<TransactionFile> flatFileItemReader =
            new FlatFileItemReader<TransactionFile>();

        flatFileItemReader.setResource(
            new FileSystemResource(
                new File(file_path)));

        flatFileItemReader.setLineMapper(new DefaultLineMapper<TransactionFile>(){
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("Banco Origem", "Agencia Origem", "Conta Origem", 
                            "Banco Destino", "Agencia Destino", "Conta Destino",
                            "Valor da transacao", "Data hora transacao");
                    }
                });
                
                setFieldSetMapper(new BeanWrapperFieldSetMapper<TransactionFile>() {
                    {
                        setTargetType(TransactionFile.class);						 
                    }

                });            
            }
        });
        return flatFileItemReader;
    }
	
	@Bean
	public ItemProcessor<TransactionFile, Transaction> processor() {
		return new TransactionProcessor();
	}
	
//	@Bean
//	public JdbcBatchItemWriter<Transaction> writer() {
//		JdbcBatchItemWriter<Transaction> itemWriter = new JdbcBatchItemWriter<Transaction>();
//
//		itemWriter.setDataSource(dataSource());
//		itemWriter.setSql("insert into student(id, first_name, last_name, email) "
//				+ "values (:id, :firstName, :lastName, :email)");
//
//		itemWriter.setItemSqlParameterSourceProvider(
//				new BeanPropertyItemSqlParameterSourceProvider<Transaction>());
//
//		return itemWriter;
//	}
	
	@Bean
	public RepositoryItemWriter<Transaction> writer() {
		RepositoryItemWriter<Transaction> writer = new RepositoryItemWriter<>();
		writer.setRepository(transactionRepository);
		writer.setMethodName("save");
		return writer;
	}

}
