package com.policeSection.driver;

import com.policeSection.driver.model.Driver;
import com.policeSection.driver.model.dto.DriverDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    DriverDTO toDto(Driver driver);

    Driver fromDto(DriverDTO driver);


}
