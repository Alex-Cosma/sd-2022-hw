package com.project.clinic.appointment;

import com.project.clinic.appointment.model.Appointment;
import com.project.clinic.appointment.model.dto.AppointmentDTO;
import com.project.clinic.user.UserService;
import com.project.clinic.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mappings({
            @Mapping(target = "dermatologistId", source = "dermatologist.id"),
            @Mapping(target = "customerId", source = "customer.id"),
            @Mapping(target = "treatmentId", source = "treatment.id"),
            @Mapping(target = "dermatologistUsername", source = "dermatologist.username"),
            @Mapping(target = "treatmentTitle", source = "treatment.title"),
    })
    AppointmentDTO toDto(Appointment appointment);

    @Mappings({
            @Mapping(target = "dermatologist", ignore = true),
            @Mapping(target = "treatment", ignore = true),
            @Mapping(target = "customer", ignore = true),
            @Mapping(target = "date", ignore = true),
    })
    Appointment fromDto(AppointmentDTO appointment);

}
