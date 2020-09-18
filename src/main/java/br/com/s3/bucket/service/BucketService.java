package br.com.s3.bucket.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import br.com.s3.bucket.model.Bucket;

public interface BucketService {
	
	/**
	 * Envia um arquivo para o S3
	 * @param multipartFile {@link MultipartFile}
	 * @return {@link Bucket}
	 */
    CompletableFuture<Bucket> uploadFile(MultipartFile multipartFile);

    /**
     * Deleta um arquivo do S3
     * @param key Chave
     * @return {@link Bucket}
     */
    CompletableFuture<Bucket> deleteFileFromS3Bucket(String key);
    
    /**
     * Coleta um arquivo do S3
     * @param key Chave
     * @return {@link Byte}
     */
    CompletableFuture<byte[]> downloadFileFromS3Bucket(String key);
}
