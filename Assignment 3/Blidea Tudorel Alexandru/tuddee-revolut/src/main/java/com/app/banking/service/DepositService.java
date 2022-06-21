package com.app.banking.service;

import com.app.banking.data.dto.mapper.DepositMapper;
import com.app.banking.data.dto.model.CardDto;
import com.app.banking.data.dto.model.DepositDto;
import com.app.banking.data.mongo.track.DepositHistoryTracker;
import com.app.banking.data.sql.entity.Card;
import com.app.banking.data.sql.entity.Deposit;
import com.app.banking.data.sql.repo.DepositRepository;
import com.app.banking.data.sql.repo.DepositTypeRepository;
import com.app.banking.data.sql.repo.UserRepository;
import com.app.banking.exception.ErrorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;
    private final DepositMapper depositMapper;
    private final UserRepository userRepository;
    private final DepositTypeRepository depositTypeRepository;
    private final DepositHistoryTracker depositHistoryTracker;

    public List<DepositDto> findAll(){
        return depositRepository.findAll().stream()
                .map(depositMapper::depositToDepositDto)
                .collect(Collectors.toList());
    }

    public List<DepositDto> getMyDeposits(String username) {
        return depositRepository.findAll().stream()
                .filter(deposit -> Objects.equals(deposit.getOwner().getUsername(), username))
                .map(depositMapper::depositToDepositDto)
                .collect(Collectors.toList());
    }

    public List<DepositDto> allDepositsByOwner(Integer id){
        return depositRepository.findAllByOwnerId(id).stream()
                .map(depositMapper::depositToDepositDto)
                .collect(Collectors.toList());
    }

    public DepositDto addDeposit(DepositDto depositDto){
        Deposit persisted = depositRepository.save(depositMapper.depositDtoToDeposit(depositDto));
        depositHistoryTracker.auditCreate(persisted);
        return depositMapper.depositToDepositDto(persisted);
    }

    public DepositDto update(Integer id, DepositDto depositDto){
        Deposit depositEntity = depositMapper.depositDtoToDeposit(depositDto);
        depositEntity.setId(id);
        Deposit original = depositRepository.findById(id)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, "Deposit to update not existing!")),
                updated = depositRepository.save(depositEntity);
        depositHistoryTracker.auditUpdate(original, updated);
        return depositMapper.depositToDepositDto(updated);
    }

    public void delete(Integer id){
        Deposit toDelete = depositRepository.findById(id)
                        .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, "Deposit not existing"));
        depositRepository.deleteById(toDelete.getId());
        depositHistoryTracker.auditDelete(toDelete);
    }

}
