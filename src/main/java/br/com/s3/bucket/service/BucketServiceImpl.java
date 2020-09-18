package br.com.s3.bucket.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import br.com.s3.bucket.model.Bucket;
import br.com.s3.bucket.util.Util;

public class BucketServiceImpl implements BucketService {

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

	@Autowired
	@Qualifier(value = "ninjaS3Client")
    private AmazonS3 amazonS3Client;

	@Async("poolTask")
	@Override
    public CompletableFuture<Bucket> uploadFile(MultipartFile multipartFile) {
        Bucket bucket = new Bucket();
        try {
            File            file         = Util.convertMultiPartToFile(multipartFile);
            String          key          = Util.generateFileName(multipartFile);
            PutObjectResult objectResult = uploadFileTos3bucket(key, file);
            URL             url          = amazonS3Client.getUrl(bucketName, key);
            
			bucket = new Bucket(url.toString(), key, objectResult.getETag(), "Arquivo enviado com sucesso!");
        } catch (Exception e) {
        	bucket.setMensagem("Arquivo não enviado := " + e.getMessage());
        }
        return CompletableFuture.completedFuture(bucket);
    }

    @Async("poolTask")
    @Override
    public CompletableFuture<Bucket> deleteFileFromS3Bucket(String key) {
    	URL url = amazonS3Client.getUrl(bucketName, key);
    	
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, key));
        
		return CompletableFuture.completedFuture(new Bucket(url.toString(), key, "", "Arquivo deletado com sucesso!"));
    }
    
    @Async("poolTask")
    @Override
    public CompletableFuture<byte[]> downloadFileFromS3Bucket(String key) {
        byte[]              content  = null;
        S3Object            s3Object = amazonS3Client.getObject(bucketName, key);
        S3ObjectInputStream stream   = s3Object.getObjectContent();
        
        try {
            content = IOUtils.toByteArray(stream);
            s3Object.close();
        } catch(final IOException ex) {
            ex.printStackTrace();
        }
        return CompletableFuture.completedFuture(content);
    }
    
    /**
     * Envia um arquivo para o S3
     * @param key Chave
     * @param file {@link File}
     * @return {@link PutObjectResult}
     */
    private PutObjectResult uploadFileTos3bucket(String key, File file) {
    	return amazonS3Client.putObject(new PutObjectRequest(bucketName, key, file)
    			.withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
