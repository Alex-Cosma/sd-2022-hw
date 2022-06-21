package com.app.banking.controller;

import com.app.banking.data.dto.model.Offer;
import com.app.banking.service.OffersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.banking.URLMapping.OFFERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(OFFERS)
@CrossOrigin(originPatterns = "http://localhost:*")
public class OfferController {

    private final OffersService offersService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Offer> getAllOffers() {
        return offersService.getOffers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Offer getOfferById(@PathVariable Integer id) {
        return offersService.getOffersById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Offer addOffer(@RequestBody Offer offer) {
        return offersService.addOffer(offer);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Offer updateOffer(@PathVariable Integer id, @RequestBody Offer offer) {
        return offersService.updateOffer(id, offer);
    }


}
