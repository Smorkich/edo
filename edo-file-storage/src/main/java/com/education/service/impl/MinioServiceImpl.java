package com.education.service.impl;

import com.education.service.MinioService;
import com.education.utils.MinioUtil;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Log4j2
public class MinioServiceImpl implements MinioService {

    private MinioClient minioClient;
    private MinioUtil util;

    /**
     * Method creates bucket named "my-bucketname" if it not exists
     * and set versioning akso.
     */
    @PostConstruct
    public void existBucket() {
        try {
            log.info("creating new bucket");
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(util.getMyBucketname()).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(util.getMyBucketname()).build());
            }
            log.info("my-bucketname is created successfully");
        } catch (MinioException e) {
            System.out.println("checking for bucket`s existence : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteObjects(String objectNumber) {
        try {
            //** Lists objects information. */
            minioClient.removeObject(RemoveObjectArgs.
                    builder()
                    .bucket(util.getMyBucketname())
                    .object(objectNumber)
                    .build());
        } catch (MinioException e) {
            System.out.println("Delete failed: " + e.getMessage());
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
            System.out.println("Connectionfailed for AKhmed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //** Upload 'my-objectname' from 'targetfolder' to 'sourcefolder' */ 
    @Override
    public void uploadOneFile(String objectName) {
        try {
            log.info("Starting to uploading");
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(util.getMyBucketname())
                            .object(objectName)
                            .filename(util.getForUploadFolder() + objectName)
                            .build());
            log.info("File is successfully uploaded to {}", util.getForUploadFolder());
        } catch (MinioException e) {
            log.error("Upload failed: ", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //** Download 'my-objectname' from 'my-bucketname' to 'targetfolder' */
    @Override
    public void downloadOneFile(String storageFileId) {

        try {
            log.info("Starting to download file: " + storageFileId);
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket(util.getMyBucketname())
                            .object(storageFileId)
                            .filename(util.getForDownloadingFolder() + storageFileId)
                            .build());
            log.info("File is successfully downloaded to " + util.getForDownloadingFolder());
        } catch (MinioException e) {
            System.out.println("Upload failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
