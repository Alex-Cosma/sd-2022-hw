package com.lab4.demo.investment;

import com.lab4.demo.investment.InvestmentRepository;
import com.lab4.demo.investment.model.Investment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final InvestmentRepository investmentRepository;

    public List<Investment> findAllByUserId(Long id){
        return investmentRepository.findAllByUserId(id);
    }
}
