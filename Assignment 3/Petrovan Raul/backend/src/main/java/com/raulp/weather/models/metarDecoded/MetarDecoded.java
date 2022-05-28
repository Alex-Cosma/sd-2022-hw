package com.raulp.weather.models.metarDecoded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MetarDecoded {
    private List<MetarDecodedData> data;
    private Integer results;
}
