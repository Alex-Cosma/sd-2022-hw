package com.project.clinic.skin_color.model;

/*
https://ro.pinterest.com/pin/784963410025699720/
 */

import java.awt.*;

public enum ESkinColor {
    VANILLA("#fff8e1"),
    BEIGE("#ffecb3") ,
    ALMOND("#ffe082"),
    GOLDEN("#8d6e63"),
    MOCHA("#5d4037"),
    TOFFEE("#3e2723");

    private String hexColorCode;

    ESkinColor(String hexColorCode){
        this.hexColorCode = hexColorCode;
    }

    public String getHexColorCode(){
        return hexColorCode;
    }
}
