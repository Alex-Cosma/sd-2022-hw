package com.project.clinic.external_api.products_api;

import com.project.clinic.product.model.dto.ProductJsonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.project.clinic.URLMapping.GET_PRODUCTS;

@Component
@RequiredArgsConstructor
public class ProductBootstrapper {

    private final WebClient productApiClient;

    public ProductJsonDTO getProductFromApi(Long id){
        Mono<ProductJsonDTO> response = productApiClient
                .get()
                .uri(GET_PRODUCTS + "/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductJsonDTO.class);

        ProductJsonDTO product = response.block();

        return product;
    }
}
