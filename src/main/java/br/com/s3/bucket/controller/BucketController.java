package br.com.s3.bucket.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.s3.bucket.model.Bucket;
import br.com.s3.bucket.service.BucketService;

@RestController
@RequestMapping("/storage/")
public class BucketController {

	@Autowired
    private BucketService bucketService;

    @PostMapping("/uploadFile")
    public ResponseEntity<Bucket> uploadFile(@RequestPart(value = "file") MultipartFile file) throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(this.bucketService.uploadFile(file).get());
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<Bucket> deleteFile(@RequestParam(value = "key") String key) throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(this.bucketService.deleteFileFromS3Bucket(key).get());
    }
    
    @GetMapping(value= "/downloadFile")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value= "key") String key) throws InterruptedException, ExecutionException {
        byte[]            data     = bucketService.downloadFileFromS3Bucket(key).get();
        ByteArrayResource resource = new ByteArrayResource(data);
        
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type",        "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + key + "\"")
                .body(resource);
    }
}
