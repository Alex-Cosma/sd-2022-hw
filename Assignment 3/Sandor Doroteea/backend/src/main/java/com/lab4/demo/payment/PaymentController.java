package com.lab4.demo.payment;

import com.lab4.demo.payment.model.DTO.PaymentDTO;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static com.lab4.demo.UrlMapping.PAYMENT;

@RestController
@RequestMapping(PAYMENT)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

//    @PostMapping
//    public void checkout(@RequestBody PaymentDTO paymentDTO) {
//        paymentService.createSession(paymentDTO);
//    }

    @RequestMapping(EXPORT_REPORT)
    public ResponseEntity<InputStreamResource> exportReport(@PathVariable ReportType type, @PathVariable Long id) throws IOException {
        FileInputStream file = new FileInputStream(paymentService.export(type,id));
        InputStreamResource inputStreamResource =  new InputStreamResource(file);
        return ResponseEntity.ok()
                .body(inputStreamResource);
    }

    @GetMapping
    public List<PaymentDTO> findAll(){
        return paymentService.findAll();
    }

    @PostMapping
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO){
        System.out.println(paymentDTO.getName() + " " + paymentDTO.getUser_id() + " " + paymentDTO.getTrack_id());
        return paymentService.savePayment(paymentDTO);
    }

}
