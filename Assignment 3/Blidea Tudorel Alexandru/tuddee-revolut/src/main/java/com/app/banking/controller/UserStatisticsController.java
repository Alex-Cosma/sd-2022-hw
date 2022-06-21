package com.app.banking.controller;

import com.app.banking.data.dto.model.UserReceivedMoneyStatistics;
import com.app.banking.data.dto.model.UserTransferStatistics;
import com.app.banking.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.app.banking.URLMapping.USERS_STATISTICS;

@RestController
@RequiredArgsConstructor
@RequestMapping(USERS_STATISTICS)
@CrossOrigin(originPatterns = "http://localhost:*")
public class UserStatisticsController {

    private final UserStatisticsService userStatisticsService;

    @PostMapping("/monthly-transfers")
    public UserTransferStatistics getMonthlyTransferStatistic(Principal principal, @RequestBody String cardNumber) {
        return userStatisticsService.getMonthlyTransferStatistic(principal.getName(), cardNumber);
    }

    @PostMapping("/monthly-received")
    public UserReceivedMoneyStatistics getMonthlyReceivedStatistic(Principal principal, @RequestBody String cardNumber) {
        return userStatisticsService.getMonthlyReceivedStatistic(principal.getName(), cardNumber);
    }

}
