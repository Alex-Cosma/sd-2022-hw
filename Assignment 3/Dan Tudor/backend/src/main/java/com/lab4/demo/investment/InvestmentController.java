package com.lab4.demo.investment;

import com.lab4.demo.investment.model.Investment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(INVESTMENTS)
@RequiredArgsConstructor
public class InvestmentController {
    private final InvestmentService investmentService;

    @GetMapping(INVESTMENTS_ID)
    public List<Investment> find(@PathVariable Long id) {
        return investmentService.findAllByUserId(id);
    }
}
