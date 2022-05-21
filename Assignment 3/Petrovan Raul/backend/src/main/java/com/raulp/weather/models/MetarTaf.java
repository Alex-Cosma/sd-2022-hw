package com.raulp.weather.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized @Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MetarTaf {
    private List<String> data;
    private Integer results;
}
