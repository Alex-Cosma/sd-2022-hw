package com.project.clinic.purchase.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Getter
@Setter
@ToString
@Builder
@Data

@Document("purchases")
public class Purchase {

    @Id
    private BigInteger id;

    private Long userId;
    private Long productId;
    private Integer price;
}
