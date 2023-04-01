package com.meng.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Configuration
@Component
@PropertySource(value = "classpath:/minio.properties")
public class MinioProp {

    /**
     * 连接url
     */
    @Value("${endpoint}")
    private String endpoint;
    /**
     * 用户名
     */
    @Value("${accesskey}")
    private String accesskey;
    /**
     * 密码
     */
    @Value("${secretKey}")
    private String secretKey;

    @Value("${bucketName}")
    private String bucketName;

}
