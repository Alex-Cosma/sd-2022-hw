package com.app.banking.config.data;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class SimpleMongoConfiguration {

    @Value("${app.mongodb.databaseName}")
    private String databaseName;

    @Value("${app.mongodb.connectionString}")
    private String connectionString;

    @Bean
    public MongoClient mongoClient() {
        ConnectionString mongodbConnectionString = new ConnectionString(connectionString);//new ConnectionString(connectionString);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(mongodbConnectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), databaseName);
    }

}
