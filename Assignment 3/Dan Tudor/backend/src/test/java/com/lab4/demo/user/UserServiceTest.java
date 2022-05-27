package com.lab4.demo.user;

import com.lab4.demo.email.EmailServiceImpl;
import com.lab4.demo.investment.model.Investment;
import com.lab4.demo.stock.StockRepository;
import com.lab4.demo.stock.StockService;
import com.lab4.demo.stock.model.Stock;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Test
    public void buy() throws Exception {
        Stock stock = Stock.builder().name("Microsoft").symbol("MSFT").build();
        Stock stock1 = Stock.builder().name("Apple").symbol("AAPL").build();
        Stock stock2 = Stock.builder().name("Google").symbol("GOOG").build();
        stockRepository.saveAll(List.of(stock, stock1, stock2));
        stockService.loadPricesForAllStocks();

        User user = User.builder().username("User").password("password").email("email@gmail.com").build();
        userRepository.save(user);
        //UserDTO userDTO = new UserDTO().builder().id(0L).username("User").password("password").email("email@gmail.com").build();
        UserDTO userDTO = userService.findById(user.getId());
        userService.buy(stock1.getId(), 7, userDTO);
        userService.buy(stock2.getId(), 10, userDTO);
        userService.buy(stock1.getId(), 5, userDTO);

        User user1 = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> new Exception("User not found"));

        assertEquals(2, user1.getInvestments().size());
        assertEquals(12,user1.getInvestments().get(0).getQuantity());
        assertEquals(10, user1.getInvestments().get(1).getQuantity());
        for(User user2 : stock1.getUsers()) {
            System.out.println(user2.getUsername());
        }
    }

    @Test
    public void sell() throws Exception {
        Stock stock = Stock.builder().name("Microsoft").symbol("MSFT").build();
        Stock stock1 = Stock.builder().name("Apple").symbol("AAPL").build();
        Stock stock2 = Stock.builder().name("Google").symbol("GOOG").build();
        stockRepository.saveAll(List.of(stock, stock1, stock2));
        stockService.loadPricesForAllStocks();

        User user = User.builder().username("User").password("password").email("email@gmail.com").build();
        userRepository.save(user);
        UserDTO userDTO = userService.findById(user.getId());
        userService.buy(stock1.getId(), 7, userDTO);
        userService.buy(stock2.getId(), 10, userDTO);
        userService.buy(stock1.getId(), 5, userDTO);
        userService.sell(stock1.getId(), 3, userDTO);
        userService.sell(stock2.getId(), 12, userDTO);

        User user1 = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> new Exception("User not found"));

        assertEquals(2, user1.getInvestments().size());
        assertEquals(9,user1.getInvestments().get(0).getQuantity());
        assertEquals(0, user1.getInvestments().get(1).getQuantity());
    }

}
