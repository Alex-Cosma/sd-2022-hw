package com.app.banking.controller;

import com.app.banking.data.dto.model.PaymentDetails;
import com.app.banking.service.PayService;
import com.app.banking.service.TransferDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.app.banking.URLMapping.PAY;

@RestController
@RequestMapping(PAY)
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:*")
public class PayController {

    private final PayService payService;

    // User could have multiple cards, in this case we should validate which one was chosen
    @PostMapping("/business")
    public void payToBusiness(Principal principal, @RequestBody @Valid PaymentDetails paymentDetails) {
        payService.payToBusiness(principal.getName(), paymentDetails);
    }

    // Same story
    @PostMapping("/transfer")
    public void transferMoney(Principal principal, @RequestBody @Valid TransferDetails transferDetails) {
        payService.makeTransfer(principal.getName(), transferDetails);
    }

}
