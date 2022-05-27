package com.lab4.demo.user;

import com.lab4.demo.email.EmailServiceImpl;
import com.lab4.demo.investment.InvestmentRepository;
import com.lab4.demo.investment.model.Investment;
import com.lab4.demo.stock.StockRepository;
import com.lab4.demo.stock.model.Stock;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final StockRepository stockRepository;
    private final InvestmentRepository investmentRepository;
    private final EmailServiceImpl emailServiceImplService;

    public UserDTO findById(Long id) {
        return userMapper.userDTOFromUser(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream().map(userMapper::userDTOFromUser)
                .collect(toList());
    }

    public UserDTO createUser(UserDTO userDTO) {
        return userMapper.userDTOFromUser(userRepository.save(userMapper.userFromUserDTO(userDTO)));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userMapper.userDTOFromUser(userRepository.save(userMapper.userFromUserDTO(userDTO)));
    }

    public void buy(Long stockID, int quantity, UserDTO userDTO){
        Stock stock = stockRepository.findById(stockID).orElseThrow(() -> new RuntimeException("Stock not found"));
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        int ok=0;
        for(int i=0;i<user.getInvestments().size();i++) {
            if(user.getInvestments().get(i).getSymbol().equals(stock.getSymbol())) {

                Investment investment = investmentRepository.findById(user.getInvestments().get(i).getId()).orElseThrow(() -> new RuntimeException("Investment not found"));
                investment.setQuantity(investment.getQuantity() + quantity);
                investmentRepository.save(investment);
                ok=1;
            }
        }
        if(ok==0){
            Investment investment1 = new Investment().builder()
                    .symbol(stock.getSymbol())
                    .quantity(quantity)
                    .price(stock.getStockPrices().get(0).getClosePrice())
                    .user(user)
                    .build();
            investmentRepository.save(investment1);

            stock.getUsers().add(user);
        }
        userRepository.save(user);
    }

    public void sell(Long stockID, int quantity, UserDTO userDTO){
        Stock stock = stockRepository.findById(stockID).orElseThrow(() -> new RuntimeException("Stock not found"));
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        int ok=0;
        Investment toBeRemoved = null;
        for(int i=0;i<user.getInvestments().size();i++) {
            Investment investment = investmentRepository.findById(user.getInvestments().get(i).getId()).orElseThrow(() -> new RuntimeException("Investment not found"));
            if (investment.getSymbol().equals(stock.getSymbol())) {
                investment.setQuantity(investment.getQuantity() - quantity);
                investmentRepository.save(investment);
                if(investment.getQuantity()<=0){
                    stock.getUsers().remove(user);
                    investment.setQuantity(0);
                    investmentRepository.delete(investment);
                }
            }
        }
    }

    public void mail(Long userId) throws MessagingException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        EmailServiceImpl emailService = new EmailServiceImpl();
        emailService.sendMail(user);
    }

}
