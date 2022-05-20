package com.lab4.demo.order;

import com.lab4.demo.order.dto.OrderDTO;
import com.lab4.demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.lab4.demo.UrlMapping.ORDERS;

@RestController
@RequestMapping(ORDERS)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @CrossOrigin
    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO orderDTO) {
        return orderService.create(orderDTO);
    }

}
