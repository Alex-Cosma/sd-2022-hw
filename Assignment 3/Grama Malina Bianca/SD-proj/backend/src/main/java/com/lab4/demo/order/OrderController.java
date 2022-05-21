package com.lab4.demo.order;

import com.lab4.demo.order.dto.OrderDTO;
import com.lab4.demo.order.service.OrderService;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(ORDERS)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ReportServiceFactory reportServiceFactory;

    @CrossOrigin
    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO orderDTO) {
        return orderService.create(orderDTO);
    }

    @CrossOrigin
    @GetMapping(ENTITY)
    public List<OrderDTO> allOrdersForUser(@PathVariable Long id) {
        return orderService.findAllForUser(id);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

    @CrossOrigin
    @GetMapping(REPORT)
    public ResponseEntity<byte[]> report(@PathVariable String type, @PathVariable Long id) throws IOException {
        if (type.equals("pdf")) {
            return reportServiceFactory.getReportService(ReportType.PDF).export(id);
        }
        return null;
    }

}
