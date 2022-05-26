package com.policeSection.fine;

import com.policeSection.driver.DriverMapper;
import com.policeSection.driver.DriverRepository;
import com.policeSection.driver.model.Driver;
import com.policeSection.driver.model.dto.DriverDTO;
import com.policeSection.fine.Model.Fine;
import com.policeSection.fine.Model.dto.FineDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FineService {
    private final FineRepository fineRepository;

    private final FineMapper fineMapper;

    private Fine findById(Long id) {
        return fineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<FineDTO> findAll() {
        return fineRepository.findAll().stream()
                .map(fineMapper::toDto)
                .collect(Collectors.toList());
    }

    public FineDTO create(FineDTO fine) {
        return fineMapper.toDto(fineRepository.save(
                fineMapper.fromDto(fine)
        ));
    }

    public FineDTO edit(Long id, FineDTO item) {
        Fine actFine = findById(id);
        actFine.setType(item.getType());
        actFine.setDescription(item.getDescription());
        actFine.setPrice(item.getPrice());
        return fineMapper.toDto(
                fineRepository.save(actFine)
        );
    }

    public void delete(Long id) {
        Fine actFine = findById(id);
        fineRepository.delete(actFine);

    }

    public Fine findByType(String type) {
        return fineRepository.findFirstByTypeEquals(type);
    }
}
