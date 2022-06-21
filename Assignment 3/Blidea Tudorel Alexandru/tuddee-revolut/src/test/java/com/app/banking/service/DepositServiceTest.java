package com.app.banking.service;

import com.app.banking.data.dto.mapper.DepositMapper;
import com.app.banking.data.dto.mapper.DepositMapperImpl;
import com.app.banking.data.dto.model.DepositDto;
import com.app.banking.data.mongo.track.DepositHistoryTracker;
import com.app.banking.data.sql.entity.Deposit;
import com.app.banking.data.sql.entity.DepositType;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.repo.DepositRepository;
import com.app.banking.data.sql.repo.DepositTypeRepository;
import com.app.banking.data.sql.repo.UserRepository;
import com.app.banking.exception.ApplicationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.app.banking.TestCreationFactory.*;
import static com.app.banking.TestHelper.getRandomIntegerBetween;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DepositServiceTest {
    @InjectMocks
    private DepositService depositService;
    @Mock
    private DepositRepository depositRepository;
    @Mock
    private DepositHistoryTracker depositHistoryTracker;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DepositTypeRepository depositTypeRepository;

    private DepositMapper depositMapper;

    @BeforeEach
    void setUp() {
        depositMapper = new DepositMapperImpl();
        MockitoAnnotations.openMocks(this);
        depositService = new DepositService(depositRepository, depositMapper, userRepository, depositTypeRepository, depositHistoryTracker);
    }

    @Test
    void testFindAll() {
        List<Deposit> deposits = new ArrayList<>();
        int noDeposits = 10;
        for (int i = 0; i < noDeposits; i++) {
            deposits.add(Deposit.builder()
                    .owner(new User())
                    .depositType(new DepositType())
                    .currentAmount(randomFloat())
                    .createdOn(getRandomDateTime())
                    .targetDate(getRandomDate())
                    .targetAmount(randomFloat())
                    .build());
        }

        when(depositRepository.findAll()).thenReturn(deposits);

        List<DepositDto> all = depositService.findAll();

        Assertions.assertEquals(noDeposits, all.size());
    }

    @Test
    void testAllDepositsByOwner() {
        Integer id = randomInteger();
        List<Deposit> deposits = new ArrayList<>();
        int noDeposits = 10;
        for (int i = 0; i < noDeposits; i++) {
            deposits.add(Deposit.builder()
                    .owner(buildUser())
                    .depositType(buildDepositType())
                    .currentAmount(randomFloat())
                    .createdOn(getRandomDateTime())
                    .targetDate(getRandomDate())
                    .targetAmount(randomFloat())
                    .build());
        }

        when(depositRepository.findAllByOwnerId(id)).thenReturn(deposits);

        List<DepositDto> foundDeposits = depositService.allDepositsByOwner(id);
        Assertions.assertEquals(noDeposits, foundDeposits.size());
    }

    @Test
    void testAddDeposit() {
        Deposit deposit = Deposit.builder()
                .owner(buildUser())
                .depositType(buildDepositType())
                .currentAmount(randomFloat())
                .createdOn(getRandomDateTime())
                .targetDate(getRandomDate())
                .targetAmount(randomFloat())
                .build();
        DepositDto depositDto = depositMapper.depositToDepositDto(deposit);

        when(depositRepository.save(any())).thenReturn(deposit);

        DepositDto savedDepositDto = depositService.addDeposit(depositDto);
        assertEquals(depositDto, savedDepositDto);
    }

//    @Test // TODO
    void testUpdateDeposit() {
        Deposit deposit = Deposit.builder()
                .owner(buildUser())
                .depositType(buildDepositType())
                .currentAmount(randomFloat())
                .createdOn(getRandomDateTime())
                .targetDate(getRandomDate())
                .targetAmount(randomFloat())
                .build();
        DepositDto depositDto = depositMapper.depositToDepositDto(deposit);

        when(depositRepository.save(any())).thenReturn(deposit);

        DepositDto savedDepositDto = depositService.update(randomInteger(), depositDto);
        assertEquals(depositDto, savedDepositDto);
    }

//    @Test // TODO
    void delete() {
        Integer id = getRandomIntegerBetween(0, 1000000);
        Deposit deposit = Deposit.builder()
                .owner(buildUser())
                .depositType(buildDepositType())
                .currentAmount(randomFloat())
                .createdOn(getRandomDateTime())
                .targetDate(getRandomDate())
                .targetAmount(randomFloat())
                .build();

        depositRepository.save(deposit);

        doNothing().when(depositRepository).deleteById(id);

        assertThrows(ApplicationError.class, () -> depositService.delete(id));

        verify(depositRepository, times(1)).deleteById(id);
    }

    private User buildUser() {
        String email = "email@employee.com";
        //String password = "Abcdefg1234!";
        return User.builder()
                .firstName(randomString())
                .lastName(randomString())
                .username(randomString())
                .email(email)
                .passwordHash(randomString())
                .role(getRandomRole())
                .birthDate(getRandomDate())
                .build();
    }

    private DepositType buildDepositType() {
        return DepositType.builder()
                .name(getRandomDepositType())
                .description(randomString())
                .interestRate(randomFloat())
                .build();
    }

    @Test
    void findAll() {
    }

    @Test
    void getMyDeposits() {
    }

    @Test
    void allDepositsByOwner() {
    }

    @Test
    void addDeposit() {
    }

    @Test
    void update() {
    }

    @Test
    void testDelete() {
    }
}
