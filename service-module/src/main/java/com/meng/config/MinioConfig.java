package com.meng.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.minio.MinioClient;

@Configuration
public class MinioConfig {


    @Autowired
    MinioProp minioProp;

    @Bean
    public MinioClient minioClient()  {

        //return new MinioClient(minioProp.getEndpoint(), minioProp.getAccesskey(), minioProp.getSecretKey());

        MinioClient minioClient = MinioClient.builder().endpoint(minioProp.getEndpoint())
                .credentials(minioProp.getAccesskey(), minioProp.getSecretKey())
                .build();
        return minioClient;
    }

}
