package com.meng.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * rest config
 * 该组件可以直接远程http访问，同时可以跳过ssl校验完成https访问
 * @author : sunyuecheng
 */
@Configuration
@PropertySource(value = {"file:${config.dir}/config/rest.properties"}, ignoreResourceNotFound = true)
public class RestConfig {

    @Value("${rest.maxConnTotal:100}")
    private int maxConnectTotal = 100;

    @Value("${rest.maxConnectPerRoute:20}")
    private int maxConnectPerRoute = 20;

    @Value("${rest.connectTimeout}")
    private int connectTimeout;

    @Value("${rest.readTimeout}")
    private int readTimeout;

    /**
     * ssl rest template
     *
     * @return org.springframework.web.client.RestTemplate :
     * @throws Exception :
     */
    @Bean("sslRestTemplate")
    public RestTemplate sslRestTemplate() throws Exception {

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, null,
                null, (hostname, session) -> true);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setSSLSocketFactory(csf)
                .setMaxConnTotal(maxConnectTotal)
                .setMaxConnPerRoute(maxConnectPerRoute)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);

        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        setRestTemplateCharset(restTemplate);
        return restTemplate;
    }

    /**
     * rest template
     *
     * @return org.springframework.web.client.RestTemplate :
     * @throws Exception :
     */
    @Bean("restTemplate")
    public RestTemplate restTemplate() throws Exception {

        CloseableHttpClient httpClient = HttpClients.custom()
                .setMaxConnTotal(maxConnectTotal)
                .setMaxConnPerRoute(maxConnectPerRoute)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        setRestTemplateCharset(restTemplate);
        return restTemplate;
    }


    private void setRestTemplateCharset(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(StandardCharsets.UTF_8);
                break;
            }
        }
    }
}
