package com.education.service.Impl;
import com.education.Utils.MinioUtil;
import com.education.service.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MinioServiceImpl implements MinioService {

    private MinioClient minioClient;
    private MinioUtil util;

    public void checkConnection() {
        try {
            List<Bucket> blist = minioClient.listBuckets();
            System.out.println(minioClient.bucketExists(BucketExistsArgs.builder().bucket("try1").build()));
            System.out.println(minioClient.getObject(GetObjectArgs.builder().bucket("try1").object("text1.txt").build()));
            System.out.println();
            System.out.println("Connection success, total buckets: " + blist.size());
            System.out.println(minioClient.getBucketTags(GetBucketTagsArgs.builder().bucket("try1").build()));
        } catch (MinioException e) {
            System.out.println("Connectionfailed for AKhmed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadOneFile(String objectName) {
        try{
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("my-bucketname")
                            .object(objectName)
                            .filename(util.getForUploadFolder() + "\\" + objectName)
                            .build());
            System.out.println( "File is successfully uploaded to " + util.getForUploadFolder());
        }catch (MinioException e) {
            System.out.println("Upload failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadOneFile(String objectName) {
        // Download 'my-objectname' from 'my-bucketname' to 'my-filename'
        try{
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket("my-bucketname")
                            .object(objectName)
                            .filename(util.getForDownloadingFolder() + "\\" +  objectName)
                            .build());
            System.out.println( "File is successfully downloaded to " + util.getForDownloadingFolder());
        }catch (MinioException e) {
            System.out.println("Upload failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public static void main(String[] args) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//
//
////        minioClient.uploadObject(
////                UploadObjectArgs.builder()
////                        .bucket("my-bucketname")
////                        .object("text2.txt")
////                        .filename("C:\\Users\\akhme\\minio\\fileHolder\\text2.txt")
////                        .build());
////        System.out.println(
////                "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
////                        + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
//
////        minioClient.downloadObject(
////                DownloadObjectArgs.builder()
////                        .bucket("try1")
////                        .object("text1.txt")
////                        .filename("text1.txt")
////                        .build());
//
//
////        minioClient.makeBucket(
////                MakeBucketArgs.builder()
////                        .bucket("my-bucketname")
////                        .build());
//    }


}
