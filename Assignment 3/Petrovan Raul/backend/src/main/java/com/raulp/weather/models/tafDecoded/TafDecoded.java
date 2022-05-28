package com.raulp.weather.models.tafDecoded;

import com.raulp.weather.models.metarDecoded.MetarDecodedData;
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
public class TafDecoded {
    private List<TafDecodedData> data;
    private Integer results;
}
