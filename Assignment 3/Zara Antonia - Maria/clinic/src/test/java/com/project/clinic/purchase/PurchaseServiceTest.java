package com.project.clinic.purchase;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.purchase.model.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        purchaseService = new PurchaseService(purchaseRepository);
    }

    @Test
    void addPurchase(){
        Purchase purchase = TestCreationFactory.newPurchase();
        when(purchaseRepository.save(purchase)).thenReturn(purchase);

        Purchase saved = purchaseService.addPurchase(purchase);

        Assertions.assertEquals(purchase.getId(), saved.getId());
    }
}
