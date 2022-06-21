package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.Contacts;
import com.app.banking.data.sql.entity.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactsRepository extends JpaRepository<Contacts, Integer> {

    List<Contacts> findContactsByPerson1IdAndRequestStatus(Integer person1Id, RequestStatus requestStatus);

    List<Contacts> findContactsByPerson1IdOrPerson2Id(Integer person1Id, Integer person2Id);
    boolean existsByPerson1IdAndPerson2Id(Integer person1Id, Integer person2Id);

}
