package com.lab4.demo.stock;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.stock.model.Stock;
import com.lab4.demo.stock.model.dto.StockDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.print.Pageable;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class StockControllerTest extends BaseControllerTest {
    @InjectMocks
    private StockController stockController;

    @Mock
    private StockService stockService;

    @BeforeEach
    public void setUp() {
        super.setUp();
        stockController = new StockController(stockService);
        mockMvc = MockMvcBuilders.standaloneSetup(stockController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void allStocks() throws Exception {
        List<StockDTO> stocks = TestCreationFactory.listOf(Stock.class);
        StockDTO stock = new StockDTO().builder().name("Apple").symbol("AAPL").build();
        StockDTO stock1 = new StockDTO().builder().name("Google").symbol("GOOG").build();
        StockDTO stock2 = new StockDTO().builder().name("Microsoft").symbol("MSFT").build();
        List<StockDTO> stocks1;
        when(stockService.findAll()).thenReturn(stocks);

        ResultActions response = mockMvc.perform(get(STOCKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(stocks));
    }

    @Test
    public void create() throws Exception {
        StockDTO stockDTO = StockDTO.builder()
                .name(randomString())
                .symbol(randomString())
                .build();

        when(stockService.create(stockDTO)).thenReturn(stockDTO);

        ResultActions result = performPostWithRequestBody(STOCKS, stockDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(stockDTO));
    }

    @Test
    public void delete() throws Exception {
        long id = randomLong();
        doNothing().when(stockService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(STOCKS + ENTITY, id);
        verify(stockService,times(1)).delete(id);

        result.andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        StockDTO stockDTO = StockDTO.builder()
                .id(randomLong())
                .name(randomString())
                .symbol(randomString())
                .build();

        when(stockService.update(stockDTO.getId(),stockDTO)).thenReturn(stockDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariable(STOCKS + ENTITY, stockDTO, stockDTO.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(stockDTO));
    }

}
