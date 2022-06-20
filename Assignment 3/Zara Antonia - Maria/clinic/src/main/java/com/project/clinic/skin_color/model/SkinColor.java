package com.project.clinic.skin_color.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SkinColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ESkinColor name;


    public static SkinColor toSkinColor( String s) {
        return new SkinColor.SkinColorBuilder().name(ESkinColor.valueOf(s)).build();
    }
}
