package com.project.clinic.product;

import com.project.clinic.BaseControllerTest;
import com.project.clinic.TestCreationFactory;
import com.project.clinic.product.model.Product;
import com.project.clinic.product.model.dto.ProductJsonDTO;
import com.project.clinic.purchase.PurchaseService;
import com.project.clinic.receipt.ReceiptService;
import com.project.clinic.user.UserService;
import com.project.clinic.user.dto.UserListDTO;
import com.project.clinic.user.model.User;
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
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProductControllerTest extends BaseControllerTest {

    @InjectMocks
    private ProductController controller;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @Mock
    private ReceiptService receiptService;

    @Mock
    private PurchaseService purchaseService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new ProductController(productService, userService, receiptService, purchaseService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allProducts() throws Exception {
        List<ProductJsonDTO> products = TestCreationFactory.listOf(ProductJsonDTO.class);
        when(productService.findAll()).thenReturn(products);

        ResultActions response = performGet(PRODUCT + FIND_ALL);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(products));
    }

    @Test
    void create() throws Exception {
        Product product = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        when(productService.create(productDto)).thenReturn(productDto);

        ResultActions response = performPostWithRequestBody(PRODUCT + ADD, productDto);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(productDto));
    }

    @Test
    void edit() throws Exception {
        Product product = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        String newName = TestCreationFactory.randomString();

        ProductJsonDTO toUpdate = ProductJsonDTO.builder()
                .id(1L)
                .name(newName)
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        ProductJsonDTO updated = ProductJsonDTO.builder()
                .id(productDto.getId())
                .name(newName)
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        when(productService.edit(productDto.getId(), toUpdate)).thenReturn(updated);

        ResultActions response = performPutWithRequestBodyAndPathVariables(PRODUCT + UPDATE + ID_PART, toUpdate, productDto.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(updated));
    }

    @Test
    void delete(){
        Product product = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        try {
            ResultActions response = performPostWithPathVariables(PRODUCT + DELETE + ID_PART, productDto.getId());
            response.andExpect(status().isOk());
        }
        catch (Exception e){
            fail("Should not have thrown exception");
        }
    }

    @Test
    void findProduct() throws Exception {
        Product product = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        when(productService.findDTOById(productDto.getId())).thenReturn(productDto);

        ResultActions response = performGetWithPathVariables(PRODUCT + FIND + ID_PART, productDto.getId());
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(productDto));
    }

    @Test
    void buyProduct() throws Exception {
        Product product = TestCreationFactory.newProduct();

        ProductJsonDTO productDto = ProductJsonDTO.builder()
                .id(1L)
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand().getName())
                .brandId(product.getBrand().getId())
                .ingredient_list(new String[0])
                .build();

        UserListDTO userListDTO = TestCreationFactory.newUserListDTO();
        User user = User.builder()
                .id(userListDTO.getId())
                .points(userListDTO.getPoints())
                .username(userListDTO.getName())
                .build();

        when(productService.findById(productDto.getId())).thenReturn(product);
        when(userService.findById(userListDTO.getId())).thenReturn(user);

        ResultActions response = performGetWithPathVariables(PRODUCT + FIND + ID_PART, productDto.getId(), userListDTO.getId());
        response.andExpect(status().isOk());

    }

}
