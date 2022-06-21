package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.Deposit;
import com.app.banking.data.sql.entity.DepositType;
import com.app.banking.data.sql.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.banking.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DepositRepositoryTest {

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepositTypeRepository depositTypeRepository;

    @BeforeEach
    public void beforeEach() {
        depositRepository.deleteAll();
        userRepository.deleteAll();
        depositTypeRepository.deleteAll();

    }

//    @Test // TODO
    public void testAddDeposit() {
        User userSaved = saveUser();
        DepositType depositType = saveDepositType();

        Deposit depositSaved = depositRepository.save(Deposit.builder()
                .owner(userSaved)
                .depositType(depositType)
                .createdOn(getRandomDateTime())
                .currentAmount(randomFloat())
                .targetAmount(randomFloat())
                .targetDate(getRandomDate())
                .build());

        assertNotNull(depositSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            depositRepository.save(Deposit.builder()
                    .build());
        });
    }


//    @Test // TODO
    public void testFindAll(){
        User userSaved = saveUser();
        DepositType depositType = saveDepositType();

        int noDeposits = 10;
        List<Deposit> deposits = new ArrayList<>();

        for (int i = 0; i < noDeposits; i++) {
            deposits.add(Deposit.builder()
                    .owner(userSaved)
                    .depositType(depositType)
                    .createdOn(getRandomDateTime())
                    .currentAmount(randomFloat())
                    .targetAmount(randomFloat())
                    .targetDate(getRandomDate())
                    .build());
        }
        depositRepository.saveAll(deposits);
        List<Deposit> all = depositRepository.findAll();
        assertEquals(deposits.size(), all.size());
    }

//    @Test // TODO
    public void testAllDepositsByOwner(){
        User userSaved = saveUser();
        DepositType depositType = saveDepositType();

        int noDeposits = 10;
        List<Deposit> deposits = new ArrayList<>();

        for (int i = 0; i < noDeposits; i++) {
            deposits.add(Deposit.builder()
                    .owner(userSaved)
                    .depositType(depositType)
                    .createdOn(getRandomDateTime())
                    .currentAmount(randomFloat())
                    .targetAmount(randomFloat())
                    .targetDate(getRandomDate())
                    .build());
        }
        depositRepository.saveAll(deposits);

        List<Deposit> all = depositRepository.findAllByOwnerId(userSaved.getId());
        assertEquals(deposits.size(), all.size());

    }

//    @Test // TODO
    public void testDeleteById(){
        User userSaved = saveUser();
        DepositType depositType = saveDepositType();


        Deposit savedDeposit = depositRepository.save(Deposit.builder()
                .owner(userSaved)
                .depositType(depositType)
                .createdOn(getRandomDateTime())
                .currentAmount(randomFloat())
                .targetAmount(randomFloat())
                .targetDate(getRandomDate())
                .build());


        depositRepository.deleteById(savedDeposit.getId());

        Optional<Deposit> result = depositRepository.findById(savedDeposit.getId());
        assertTrue(result.isEmpty());
    }

    private User saveUser(){
        String email = "email@employee.com";
        //String password = "Abcdefg1234!";
        return userRepository.save(User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
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
