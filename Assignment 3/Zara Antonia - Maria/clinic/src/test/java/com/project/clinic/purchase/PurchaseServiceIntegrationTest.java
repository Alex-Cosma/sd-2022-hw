package com.project.clinic.purchase;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.purchase.model.Purchase;
import com.project.clinic.skin_color.SkinColorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PurchaseServiceIntegrationTest {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @BeforeEach
    void setUp(){
        purchaseRepository.deleteAll();
    }

    @Test
    void addPurchase(){
        Purchase purchase = TestCreationFactory.newPurchase();

        Purchase saved = purchaseService.addPurchase(purchase);

        Assertions.assertEquals(purchase.getUserId(), saved.getUserId());
        Assertions.assertEquals(purchase.getProductId(), saved.getProductId());
    }

}
