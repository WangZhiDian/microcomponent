package com.meng.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Configuration
@PropertySource(value = "file:${config.dir}/config/minio.properties")
public class MinioProp {

    /**
     * 连接url
     */
    @Value("${minio.endpoint}")
    private String endpoint;
    /**
     * 用户名
     */
    @Value("${minio.accessKey}")
    private String accesskey;

    /**
     * 密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

}
