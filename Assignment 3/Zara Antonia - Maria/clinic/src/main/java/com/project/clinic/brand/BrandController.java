package com.project.clinic.brand;

import com.project.clinic.brand.model.dto.BrandDTO;
import com.project.clinic.product.model.dto.ProductJsonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.project.clinic.URLMapping.*;

@RestController
@RequestMapping(BRAND)
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class BrandController {

    private final BrandService brandService;

    @GetMapping(FIND_ALL)
    public List<BrandDTO> allBrands() {
        List<BrandDTO>  brandDTOS = brandService.findAll();
        return brandDTOS;
    }
}
