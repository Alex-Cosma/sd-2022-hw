package com.project.clinic.purchase;

import com.project.clinic.purchase.model.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public Purchase addPurchase(Purchase purchase){
        return purchaseRepository.save(purchase);
    }
}
