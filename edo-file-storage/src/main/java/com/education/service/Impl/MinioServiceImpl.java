package com.education.service.Impl;
import com.education.Utils.MinioUtil;
import com.education.service.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class MinioServiceImpl implements MinioService {

    private MinioClient minioClient;
    private MinioUtil util;

    /** Method creates bucket named "my-bucketname" if it not exists
     *  and set versioning akso. */
    @PostConstruct
    public void existBucket() {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket("my-bucketname").build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("my-bucketname").build());
                System.out.println("my-bucketname is created successfully");
            }
//            // Enable versioning on 'my-bucketname'
//            minioClient.setBucketVersioning(SetBucketVersioningArgs.builder().bucket("my-bucketname").build());
//            System.out.println("Bucket versioning is enabled successfully");
        } catch (MinioException e) {
            System.out.println("checking for bucket`s existence : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteObjects()
    {
        Map<String, ZonedDateTime> lastmdf = new HashMap<>();
        try{
            // Lists objects information.
            Iterable<Result<Item>> results =
                    minioClient.listObjects(ListObjectsArgs.builder().bucket("my-bucketname").build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.lastModified().getDayOfMonth() > 27 ) {
                    minioClient.removeObject(RemoveObjectArgs.
                            builder()
                            .bucket("my-bucketname")
                            .object(item.objectName())
                            .versionId(item.versionId())
                            .build());
                }
                lastmdf.put(item.objectName(),item.lastModified());
                System.out.println(item.lastModified() + "\t" + item.size() + "\t" + item.objectName());
            }
            System.out.println(lastmdf);
        }catch (MinioException e) {
            System.out.println("Delete failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



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
                            .filename(util.getForUploadFolder() + objectName)
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
                            .filename(util.getForDownloadingFolder()  +  objectName)
                            .build());
            System.out.println( "File is successfully downloaded to " + util.getForDownloadingFolder());
        }catch (MinioException e) {
            System.out.println("Upload failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
