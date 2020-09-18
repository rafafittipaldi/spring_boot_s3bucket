package br.com.s3.bucket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@EnableAsync
public class BucketConfig {

    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    
    /**
     * Cria o Bean com Amazon S3 WS
     * Altere somente a Região conforme necessidade do seu Bucket.
     * @return {@link AmazonS3}
     */
    @Bean(name = "amazonS3Client")
    @Primary
    public AmazonS3 amazonS3Client() {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withRegion(Regions.SA_EAST_1)
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
					.build();
              
            return s3Client;
    }
    
    /**
     * Cria o Bean com Ninja S3
     * Altere para qualquer Região
     * @return {@link AmazonS3}
     */
	@Bean(name = "ninjaS3Client")
	public AmazonS3 ninjaS3Client() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3Client = AmazonS3ClientBuilder
				.standard()
				.withEndpointConfiguration(new  AwsClientBuilder
						.EndpointConfiguration("http://localhost:9444/s3", Regions.SA_EAST_1.getName()))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withPathStyleAccessEnabled(true)
				.build();

		return s3Client;
	}
    
	/**
	 * Cria o Bean de executor de tarefas com prefico "Async-"
	 * @return {@link TaskExecutor}
	 */
	@Bean("poolTask")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize                    (20);
		executor.setMaxPoolSize                     (1000);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix                ("Async-");
		return executor;
	}
}
