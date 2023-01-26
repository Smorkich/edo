package com.education.component;

import com.education.exceptions.MinIOPutException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Log4j2
@RequiredArgsConstructor
@Component
public class MinioComponent {

    private final MinioClient minioClient;


    @Value("${minio.bucket-name}")
    private String bucketName;

    public void postObject(String objectName, InputStream inputStream, String contentType) {
        try (inputStream) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .contentType(contentType)
                    .stream(inputStream, -1, 104857600)
                    .build());
        } catch (Exception e) {
            log.error("Error while put object in MinIO {}", e.getMessage());
            throw new MinIOPutException(e.getMessage());
        }
    }

    public InputStream getObject(String objectName) {

        InputStream stream = null;
        try {
            stream = minioClient
                    .getObject(GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            log.error("Upload failed: {}",e.getMessage());
        }
        return stream;
    }

    public void deleteObjects(String objectNumber) {
        try {
            minioClient.removeObject(RemoveObjectArgs.
                    builder()
                    .bucket(bucketName)
                    .object(objectNumber)
                    .build());
        } catch (MinioException e) {
            log.error("Delete failed: {}", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkConnection() {
        try {
            log.info("Starting connection to MINIO server");
            List<Bucket> blist = minioClient.listBuckets();
            log.info("Connection success, total buckets: " + blist.size());
        } catch (MinioException e) {
            log.error("Connection failed: {}", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
