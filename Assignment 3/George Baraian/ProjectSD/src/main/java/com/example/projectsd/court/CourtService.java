package com.example.projectsd.court;

import com.example.projectsd.court.model.Court;
import com.example.projectsd.court.model.dto.CourtDTO;
import com.example.projectsd.email.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final CourtRepository courtRepository;
    private final CourtMapper courtMapper;
    @Autowired
    private EmailSenderService emailSenderService;

    public CourtDTO findById(Long id){
        return courtMapper.toDto(courtRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Court not found: " + id)));
    }

    public CourtDTO create(CourtDTO court) {
        return courtMapper.toDto(courtRepository.save(
                courtMapper.fromDto(court)
        ));
    }

    public List<CourtDTO> findAll() {

        return courtRepository.findAll().stream()
                .map(courtMapper::toDto)
                .collect(Collectors.toList());

    }

    public CourtDTO edit(CourtDTO court) {
        Court court1 = courtMapper.fromDto(findById(court.getId()));
        court1.setName(court.getName());
        court1.setAvailability(court.getAvailability());
        court1.setSportType(court.getSportType());
        return courtMapper.toDto(((courtRepository.save(court1))));
    }

    public void delete(Long id) {
        courtRepository.deleteById(id);
    }

    public CourtDTO changeName(Long id, String newName) {
        Court court = courtMapper.fromDto(findById(id));
        court.setName(newName);
        return courtMapper.toDto(
                courtRepository.save(court)
        );
    }

    public void update(CourtDTO courtDTO) {
        Court court = courtMapper.fromDto(courtDTO);
        courtRepository.save(court);
    }
}
