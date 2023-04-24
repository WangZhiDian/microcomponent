package com.meng.minio.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "file:${config.dir}/config/minio.properties", ignoreResourceNotFound = true)
@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    String endPoint;

    @Value("${minio.accessKey}")
    String accessKey;

    @Value("${minio.secretKey}")
    String secretKey;

    @Value("${minio.bucketName}")
    String bucket;


    @Bean("minioClient")
    public MinioClient minioClients() {
        MinioClient minioClient =
            MinioClient.builder()
                .endpoint(endPoint)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }


}
