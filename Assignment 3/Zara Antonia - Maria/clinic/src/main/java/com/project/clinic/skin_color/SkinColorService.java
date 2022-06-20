package com.project.clinic.skin_color;

import com.project.clinic.skin_color.model.ESkinColor;
import com.project.clinic.skin_color.model.SkinColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SkinColorService {

    private final SkinColorRepository skinColorRepository;

    public List<SkinColor> findAll() {
        return skinColorRepository.findAll();
    }

    public SkinColor findById(Long id){
        return skinColorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skin color not found: " + id));
    }

    public SkinColor findByName(ESkinColor skinColor){
        return skinColorRepository.findByName(skinColor);
    }
}
