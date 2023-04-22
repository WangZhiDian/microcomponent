package com.meng.es.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.elasticsearch.ElasticsearchRestHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

/**
 * spring elasticsearch config
 *
 * @author : sunyuecheng
 */
@Configuration
@PropertySource(value = "file:${config.dir}/config/elasticsearch.properties", ignoreResourceNotFound = true)
@ConditionalOnProperty(name = "system.bean.switch.elasticsearch", havingValue = "true", matchIfMissing = true)
public class SpringDataEsConfig extends AbstractElasticsearchConfiguration {

    @Value("${es.addressPorts}")
    private String addressPorts;

    @Value("${es.usingSsl}")
    private boolean usingSsl = false;

    @Value("${es.userName}")
    private String userName;

    @Value("${es.password}")
    private String password;

    @Value("${es.connectTimeout}")
    private int connectTimeout = 1000 * 10;

    @Value("${es.socketTimeout}")
    private int socketTimeout = 1000 * 10;

    /**
     * elasticsearch rest client
     *
     * @return RestClients.ElasticsearchRestClient :
     */
    @Bean("esRestClient")
    public RestClients.ElasticsearchRestClient elasticsearchRestClient() {

        ClientConfiguration.ClientConfigurationBuilderWithRequiredEndpoint clientConfigurationBuilder
                = ClientConfiguration.builder();

        String[] addressPortList = addressPorts.split(";");
        ClientConfiguration.MaybeSecureClientConfigurationBuilder maybeSecureClientConfigurationBuilder
                = clientConfigurationBuilder.connectedTo(addressPortList);
        ClientConfiguration.TerminalClientConfigurationBuilder terminalClientConfigurationBuilder = null;

        if (usingSsl) {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

            SSLContext sslContext = null;
            HostnameVerifier hostnameVerifier = null;
            try {
                sslContext = SSLContexts.custom()
                        .loadTrustMaterial(null, acceptingTrustStrategy)
                        .build();
                hostnameVerifier = (hostname, session) -> true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            terminalClientConfigurationBuilder = maybeSecureClientConfigurationBuilder
                    .usingSsl(sslContext, hostnameVerifier)
                    .withSocketTimeout(socketTimeout).withConnectTimeout(connectTimeout);

        } else {
            terminalClientConfigurationBuilder = maybeSecureClientConfigurationBuilder
                    .withSocketTimeout(socketTimeout).withConnectTimeout(connectTimeout);
        }

        if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)) {
            terminalClientConfigurationBuilder
                    = terminalClientConfigurationBuilder.withBasicAuth(userName, password);
        }
        ClientConfiguration clientConfiguration = terminalClientConfigurationBuilder.build();

        return RestClients.create(clientConfiguration);
    }

    /**
     * elasticsearch rest health indicator
     *
     * @return ElasticsearchRestHealthIndicator :
     */
    @Bean
    public ElasticsearchRestHealthIndicator elasticsearchRestHealthIndicator() {
        return new ElasticsearchRestHealthIndicator(elasticsearchRestClient().lowLevelRest());
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        return elasticsearchRestClient().rest();
    }

}
