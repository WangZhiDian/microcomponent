package com.meng.neo.config;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.driver.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.convert.Neo4jConversions;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * spring elasticsearch config
 *
 * @author : sunyuecheng
 */
@Configuration
@PropertySource(value = "file:${config.dir}/config/neoj4.properties", ignoreResourceNotFound = true)
@ConditionalOnProperty(name = "system.bean.switch.neoj4", havingValue = "true", matchIfMissing = true)
public class SpringDataNeo4jConfig {

    @Value("${neo4j.uri}")
    private String uri;

    @Value("${neo4j.database}")
    private String database;

    @Value("${neo4j.userName}")
    private String userName;

    @Value("${neo4j.password}")
    private String password;

    @Value("${neo4j.connectTimeout}")
    private int connectTimeout = 1000 * 30;

    @Value("${neo4j.maxConnectionPoolSize}")
    private int maxConnectionPoolSize = 100;

    @Bean
    public Driver neo4jDriver() {
        AuthToken authToken = AuthTokens.none();
        if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)) {
            authToken = AuthTokens.basic(userName, password, null);
        }
        Config.ConfigBuilder builder = Config.builder();
        builder.withConnectionTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        builder.withMaxConnectionPoolSize(maxConnectionPoolSize);

        return GraphDatabase.driver(uri, authToken, builder.build());
    }

    @Bean
    public Neo4jConversions neo4jConversions() {
        return new Neo4jConversions();
    }

    @Bean
    public Neo4jMappingContext neo4jMappingContext(ApplicationContext applicationContext,
                                                   Neo4jConversions neo4jConversions) throws ClassNotFoundException {
        Set<Class<?>> initialEntityClasses = new EntityScanner(applicationContext).scan(Node.class,
                RelationshipProperties.class);
        Neo4jMappingContext context = new Neo4jMappingContext(neo4jConversions);
        context.setInitialEntitySet(initialEntityClasses);
        return context;
    }

    @Bean
    public DatabaseSelectionProvider databaseSelectionProvider() {
        return !StringUtils.isEmpty(database) ? DatabaseSelectionProvider.createStaticDatabaseSelectionProvider(database)
                : DatabaseSelectionProvider.getDefaultSelectionProvider();
    }

    @Bean
    public Neo4jClient neo4jClient(Driver driver, DatabaseSelectionProvider databaseNameProvider) {
        return Neo4jClient.create(driver, databaseNameProvider);
    }

    @Bean
    public Neo4jTemplate neo4jTemplate(Neo4jClient neo4jClient, Neo4jMappingContext neo4jMappingContext) {
        return new Neo4jTemplate(neo4jClient, neo4jMappingContext);
    }
}
