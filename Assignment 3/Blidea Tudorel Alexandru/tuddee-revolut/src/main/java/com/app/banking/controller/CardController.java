package com.app.banking.controller;

import com.app.banking.data.dto.model.CardDto;
import com.app.banking.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.app.banking.URLMapping.*;

@RestController
@RequestMapping(CARDS)
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:*")
public class CardController {

    private final CardService cardService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<CardDto> allCards() {
        return cardService.findAll();
    }

    @GetMapping(MY_CARDS)
    public List<CardDto> allCardsByOwner(Principal principal) {
        return cardService.allCardsByOwner(principal.getName());
    }

    @PostMapping()
    public CardDto create(Principal principal, @RequestBody CardDto card) {
        return cardService.addCard(principal.getName(), card);
    }

    @PatchMapping(ID)
    public CardDto update(@PathVariable Integer id, @RequestBody CardDto card) {
        return cardService.update(id, card);
    }

    @DeleteMapping(ID)
    public void delete(@PathVariable Integer id) {
        cardService.delete(id);
    }

}
