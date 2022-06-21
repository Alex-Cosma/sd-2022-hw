package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.Transfer;
import com.app.banking.data.sql.entity.enums.TransferStatus;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferRepository extends CrudRepository<Transfer, Integer> {

    List<Transfer> findAllByTransferStatus(TransferStatus transferStatus);
    List<Transfer> findAllByStartedOnBeforeAndFrom(LocalDateTime date, String from);
    List<Transfer> findAllByStartedOnBeforeAndTo(LocalDateTime date, String to);

}
