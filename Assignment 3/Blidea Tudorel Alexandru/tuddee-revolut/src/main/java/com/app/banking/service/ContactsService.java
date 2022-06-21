package com.app.banking.service;

import com.app.banking.data.dto.model.ContactsDto;
import com.app.banking.data.sql.entity.Card;
import com.app.banking.data.sql.entity.Contacts;
import com.app.banking.data.sql.entity.User;
import com.app.banking.data.sql.entity.enums.RequestStatus;
import com.app.banking.data.sql.repo.CardRepository;
import com.app.banking.data.sql.repo.ContactsRepository;
import com.app.banking.data.sql.repo.UserRepository;
import com.app.banking.exception.ErrorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ContactsService {

    private final UserRepository userRepository;
    private final ContactsRepository contactsRepository;
    private final CardRepository cardRepository;

    public List<ContactsDto> getMyContacts(String username) {
        return getMyContactsByRequestStatus(username, RequestStatus.ACCEPTED);
    }

    public List<ContactsDto> getMyPendingContacts(String username) {
        return getMyContactsByRequestStatus(username, RequestStatus.PENDING);
    }

    public void addContact(String username, String email) {
        User myself = userRepository.findByUsername(username)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND));
        User friend = userRepository.findByEmail(email)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND));
        if (contactsRepository.existsByPerson1IdAndPerson2Id(myself.getId(), friend.getId()) ||
                contactsRepository.existsByPerson1IdAndPerson2Id(friend.getId(), myself.getId())) {
            throw ErrorFactory.getError(HttpStatus.CONFLICT);
        }
        Contacts contacts = Contacts.builder()
                .person1Id(myself.getId())
                .person2Id(friend.getId()).
                requestStatus(RequestStatus.PENDING)
                .build();
    }

    private List<ContactsDto> getMyContactsByRequestStatus(String username, RequestStatus requestStatus) {
        User myself = userRepository.findByUsername(username)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND));
        return contactsRepository.findContactsByPerson1IdOrPerson2Id(myself.getId(), myself.getId()).stream()
                .filter(contact -> Objects.equals(contact.getRequestStatus(), requestStatus))
                .map(contact -> {
                    User contactDetails = userRepository.findById(getOtherPersonId(myself.getId(), contact)).get();
                    List<String> ibans = cardRepository.findAllByOwnerId(contactDetails.getId()).stream()
                            .map(Card::getIban)
                            .collect(Collectors.toList());
                    return ContactsDto.builder()
                            .id(contact.getId())
                            .lastName(contactDetails.getLastName())
                            .firstName(contactDetails.getFirstName())
                            .email(contactDetails.getEmail())
                            .ibans(ibans)
                            .profileImageName(contactDetails.getProfileImageName())
                            .build();
                }).collect(Collectors.toList());
    }

    private Integer getOtherPersonId(Integer myId, Contacts contact) {
        return contact.getPerson1Id() + contact.getPerson2Id() - myId;
    }

}
