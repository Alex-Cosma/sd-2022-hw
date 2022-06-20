package com.project.clinic.purchase;

import com.project.clinic.purchase.model.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface PurchaseRepository extends MongoRepository<Purchase, BigInteger> {
}
