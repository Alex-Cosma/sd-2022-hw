package com.project.clinic.external_api.products_api;

import com.project.clinic.product.model.dto.ProductJsonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.project.clinic.URLMapping.GET_PRODUCTS;
import static com.project.clinic.URLMapping.PRODUCT_BASE_URL;

@Component
@RequiredArgsConstructor
public class ProductWebClient {

    @Bean
    public WebClient productApiClient(){
        return WebClient.create(PRODUCT_BASE_URL);
    }
}
