package com.project.clinic.brand;

import com.project.clinic.BaseControllerTest;
import com.project.clinic.TestCreationFactory;
import com.project.clinic.brand.model.dto.BrandDTO;
import com.project.clinic.product.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.project.clinic.URLMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BrandControllerTest extends BaseControllerTest {

    @InjectMocks
    private BrandController controller;

    @Mock
    private BrandService brandService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BrandController(brandService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allBrands() throws Exception {
        List<BrandDTO> brandDTOS = TestCreationFactory.listOf(BrandDTO.class);

        when(brandService.findAll()).thenReturn(brandDTOS);

        ResultActions response = performGet(BRAND + FIND_ALL);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(brandDTOS));
    }
}
