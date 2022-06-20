package com.project.clinic.skin_color;

import com.project.clinic.skin_color.model.ESkinColor;
import com.project.clinic.skin_color.model.SkinColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkinColorRepository extends JpaRepository<SkinColor, Long>{
    SkinColor findByName(ESkinColor skinColor);
}
