package com.app.banking.service;

import com.app.banking.data.dto.mapper.OffersMapper;
import com.app.banking.data.dto.model.Offer;
import com.app.banking.data.sql.entity.Offers;
import com.app.banking.data.sql.repo.OffersRepository;
import com.app.banking.exception.ErrorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OffersService {

    private final OffersRepository offersRepository;
    private final OffersMapper offersMapper;

    public Offer getOffersById(Integer id) {
        return offersRepository.findById(id)
                .map(offersMapper::entityToDto)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND));
    }

    public List<Offer> getOffers() {
        return offersRepository.findAll().stream()
                .map(offersMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<Offer> getAvailableOffers() {
        return offersRepository.findByAvailable(true).stream()
                .map(offersMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public Offer addOffer(Offer dto) {
        Offers toPersist = offersMapper.dtoToEntity(dto);
        toPersist.setId(null);

        return offersMapper.entityToDto(offersRepository.save(toPersist));
    }

    public Offer updateOffer(Integer id, Offer dto) {
        if (!Objects.equals(id, dto.getId())) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, "Ids must be the same!");
        }
        Offers toUpdate = offersRepository.findById(id)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND));

        return offersMapper.entityToDto(offersRepository.save(toUpdate));
    }

}
