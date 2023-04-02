package com.meng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"com.meng"})
public class AccessService {
    public static void main(String[] args) {
        SpringApplication.run(AccessService.class, args);
    }
}