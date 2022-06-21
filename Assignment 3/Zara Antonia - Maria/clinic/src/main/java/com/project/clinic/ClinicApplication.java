package com.project.clinic;

import com.project.clinic.external_api.weather_api.WeatherController;
import com.project.clinic.purchase.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = PurchaseRepository.class)
public class ClinicApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ClinicApplication.class, args);
    }

}
