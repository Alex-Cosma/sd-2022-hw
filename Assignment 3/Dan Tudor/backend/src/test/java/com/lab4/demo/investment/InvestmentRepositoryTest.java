package com.lab4.demo.investment;

import com.lab4.demo.investment.model.Investment;
import com.lab4.demo.stock.StockRepository;
import com.lab4.demo.stock.model.Stock;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class InvestmentRepositoryTest {
    @Autowired
    InvestmentRepository investmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StockRepository stockRepository;

    @BeforeEach
    @AfterEach
    public void beforeAll() {
        investmentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void findAllByUserId(){
        Stock stock = new Stock().builder().name("Apple").symbol("AAPL").build();
        stockRepository.save(stock);

        User user = User.builder().username("User").password("password").email("email@gmail.com").build();
        userRepository.save(user);

        Investment investment = new Investment().builder().symbol("AAPL").quantity(10).user(user).build();
        investmentRepository.save(investment);

        List<Investment> investments = investmentRepository.findAllByUserId(user.getId());

        assertTrue(investments.size() == 1);
    }
}
