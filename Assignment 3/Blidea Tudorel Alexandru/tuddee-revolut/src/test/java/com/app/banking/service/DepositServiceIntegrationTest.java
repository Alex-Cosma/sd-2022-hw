package com.app.banking.service;

import com.app.banking.data.dto.mapper.DepositMapper;
import com.app.banking.data.dto.mapper.DepositMapperImpl;
import com.app.banking.data.dto.model.DepositDto;
import com.app.banking.data.sql.entity.*;
import com.app.banking.data.sql.repo.DepositRepository;
import com.app.banking.data.sql.repo.DepositTypeRepository;
import com.app.banking.data.sql.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.app.banking.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DepositServiceIntegrationTest {
    @Autowired
    private DepositService depositService;
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private DepositTypeRepository depositTypeRepository;
    @Autowired
    private UserRepository userRepository;

    private DepositMapper depositMapper;

    @BeforeEach
    void setUp() {
        depositMapper = new DepositMapperImpl();

        userRepository.deleteAll();
        depositTypeRepository.deleteAll();
        depositRepository.deleteAll();
    }

//    @Test // TODO
    void testFindAll() {
        User userSaved = saveUser();
        DepositType depositType = saveDepositType();

        List<Deposit> deposits = new ArrayList<>();
        int noDeposits = 10;
        for (int i = 0; i < noDeposits; i++) {
            deposits.add(Deposit.builder()
                    .owner(userSaved)
                    .depositType(depositType)
                    .currentAmount(randomFloat())
                    .createdOn(getRandomDateTime())
                    .targetDate(getRandomDate())
                    .targetAmount(randomFloat())
                    .build());
        }
        depositRepository.saveAll(deposits);

        List<DepositDto> all = depositService.findAll();

        assertEquals(noDeposits, all.size());
    }

//    @Test // TODO
    void testAllDepositsByOwner(){
        User userSaved = saveUser();
        DepositType depositType = saveDepositType();

        List<Deposit> deposits = new ArrayList<>();
        int noDeposits = 10;
        for (int i = 0; i < noDeposits; i++) {
            deposits.add(Deposit.builder()
                    .owner(userSaved)
                    .depositType(depositType)
                    .currentAmount(randomFloat())
                    .createdOn(getRandomDateTime())
                    .targetDate(getRandomDate())
                    .targetAmount(randomFloat())
                    .build());
        }
        depositRepository.saveAll(deposits);

        List<DepositDto> fundDeposits = depositService.allDepositsByOwner(userSaved.getId());
        assertEquals(noDeposits, fundDeposits.size());
    }

//    @Test // TODO
    void testAddDeposit(){
        User userSaved = saveUser();
        DepositType depositType = saveDepositType();
        Deposit deposit = Deposit.builder()
                .owner(userSaved)
                .depositType(depositType)
                .currentAmount(randomFloat())
                .createdOn(getRandomDateTime())
                .targetDate(getRandomDate())
                .targetAmount(randomFloat())
                .build();

        DepositDto depositDto = depositMapper.depositToDepositDto(deposit);

        DepositDto savedDepositDto = depositService.addDeposit(depositDto);

        assertEquals(savedDepositDto, depositDto);
    }

//    @Test // TODO
    void testUpdateDeposit(){
        User userSaved = saveUser();
        DepositType depositType = saveDepositType();
        Deposit deposit = Deposit.builder()
                .owner(userSaved)
                .depositType(depositType)
                .currentAmount(randomFloat())
                .createdOn(getRandomDateTime())
                .targetDate(getRandomDate())
                .targetAmount(randomFloat())
                .build();

        DepositDto depositDto = depositMapper.depositToDepositDto(deposit);

        DepositDto savedDepositDto = depositService.update(1, depositDto);

        assertEquals(savedDepositDto, depositDto);
    }

    private User saveUser(){

        //String password = "Abcdefg1234!";
        return userRepository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(randomString() + "@email.com")
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build());
    }

    private DepositType saveDepositType(){
        return depositTypeRepository.save(DepositType.builder()
                .name(getRandomDepositType())
                .description(randomString())
                .interestRate(randomFloat())
                .build());
    }
}
