package com.mozi.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class S3FileManager implements FileManager {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile file, String dirName) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String key = dirName + "/" + fileName;

        PutObjectRequest request = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(file.getContentType())
            .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        return key;
    }

    @Override
    public String getUrl(String key) {
        return s3Client.utilities().
            getUrl(a -> a.bucket(bucket).key(key))
            .toExternalForm();
    }

    @Override
    public void delete(String key) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build());
    }
}
