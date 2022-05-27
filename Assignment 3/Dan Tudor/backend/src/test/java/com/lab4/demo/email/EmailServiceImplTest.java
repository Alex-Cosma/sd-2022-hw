package com.lab4.demo.email;

import com.lab4.demo.investment.InvestmentRepository;
import com.lab4.demo.investment.model.Investment;
import com.lab4.demo.stock.StockRepository;
import com.lab4.demo.stock.model.Stock;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class EmailServiceImplTest {
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    InvestmentRepository investmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StockRepository stockRepository;

    @Test
    public void testSendEmailWithAttachment() throws MessagingException {
        Stock stock = new Stock().builder().name("Apple").symbol("AAPL").build();
        stockRepository.save(stock);

        User user = User.builder().username("User").password("password").email("tudordan25@gmail.com").build();

        Investment investment = new Investment().builder().symbol("AAPL").quantity(10).user(user).build();
        List<Investment> investments = new ArrayList<>();
        investments.add(investment);
        user.setInvestments(investments);
        user.getInvestments().forEach(System.out::println);
        emailService.sendMail(user);
    }
}
