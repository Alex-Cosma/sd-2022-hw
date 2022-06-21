package com.project.clinic.product;

import com.project.clinic.product.model.Product;
import com.project.clinic.product.model.dto.ProductJsonDTO;
import com.project.clinic.purchase.PurchaseService;
import com.project.clinic.purchase.model.Purchase;
import com.project.clinic.receipt.ReceiptService;
import com.project.clinic.receipt.dto.ReceiptDTO;
import com.project.clinic.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import static com.project.clinic.URLMapping.*;

@RestController
@RequestMapping(PRODUCT)
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ReceiptService receiptService;
    private final PurchaseService purchaseService;

    @GetMapping(FIND_ALL)
    public List<ProductJsonDTO> allProducts() {
        List<ProductJsonDTO>  productDTOS = productService.findAll();
        return productDTOS;
    }

    @PostMapping(ADD)
    public ProductJsonDTO create(@RequestBody ProductJsonDTO product) {
        return productService.create(product);
    }

    @PostMapping(DELETE + ID_PART)
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }

    @PutMapping(UPDATE + ID_PART)
    public ProductJsonDTO edit(@PathVariable Long id, @RequestBody ProductJsonDTO productUpdate) {
        return productService.edit(id, productUpdate);
    }

    @GetMapping(FIND + ID_PART)
    public ProductJsonDTO findProduct(@PathVariable Long id){
        return productService.findDTOById(id);
    }

    @GetMapping(BUY + PRODUCT_ID_PART + USERS_ID_PART)
    public ResponseEntity<Resource> buyProduct(@PathVariable Long id, @PathVariable Long productId ) throws IOException {
        Product product = productService.findById(productId);

        //modify points for user
        userService.buy(id, productId, product.getPrice());

        //create purchase
        purchaseService.addPurchase(Purchase.builder()
                .userId(id)
                .productId(productId)
                .price(product.getPrice())
                .build());

        //create receipt
        File file = receiptService.export(
                new ReceiptDTO("Receipt", product.getName(), product.getPrice(), LocalDateTime.now().toString())
        );

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int)file.length()];
        fl.read(arr);

        ByteArrayResource resource = new ByteArrayResource(arr);

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
