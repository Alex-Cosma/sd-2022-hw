package com.app.banking.controller;

import com.app.banking.data.dto.model.ContactRequestDto;
import com.app.banking.data.dto.model.ContactsDto;
import com.app.banking.service.ContactsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.app.banking.URLMapping.CONTACTS;

@RestController
@RequiredArgsConstructor
@RequestMapping(CONTACTS)
@CrossOrigin(originPatterns = "http://localhost:*")
public class ContactsController {

    private final ContactsService contactsService;

    @GetMapping("/my")
    public List<ContactsDto> getMyContacts(Principal principal) {
        return contactsService.getMyContacts(principal.getName());
    }

    @GetMapping("/my/pending")
    public List<ContactsDto> getMyPendingContacts(Principal principal) {
        return contactsService.getMyPendingContacts(principal.getName());
    }

    @PostMapping("/add")
    public void addContact(Principal principal, @RequestBody ContactRequestDto contactRequestDto) {
        contactsService.addContact(principal.getName(), contactRequestDto.getContactEmail());
    }

}
