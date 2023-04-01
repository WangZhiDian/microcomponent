package com.meng;


import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class ServiceApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(ServiceApplication.class, args);
        /*try {
            test();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }*/
    }

    public static void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://172.16.40.70:9000")
                            .credentials("NML35PJ3E1F2JK0B6WE1",
                                    "cS0M0EaowuBLq784hGe9OMRaCAfa4FI8djV1oRgE")
                            .build();

            // Make 'asiatrip' bucket if not exist.
            String bucketName = "public-info";
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("Bucket 'public' already exists.");
            }

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object("desktop05.jpeg")
                            .filename("/Users/wangmeng/Pictures/desktop05.jpeg")
                            .build());
            System.out.println("'desktop05.jpeg' is successfully uploaded as " + "object 'desktop05.jpeg' to bucket 'public-info'.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            //System.out.println("HTTP trace: " + e.httpTrace());
        }
    }


}