package com.app.banking.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ServiceUtil {

    @Value("${app.serviceUtil.dateFormatter}")
    private String dateFormatter;
    @Value("${app.serviceUtil.dateTimeFormatter}")
    private String dateTimeFormatter;
    @Value("${app.serviceUtil.bankName}")
    private String bankName;

    private ServiceUtil() {

    }

    public LocalDate formatStringToDate(String string) {
        return LocalDate.parse(string, DateTimeFormatter.ofPattern(dateFormatter));
    }

    public String formatDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(dateFormatter));
    }

    public String formatDateTimeToString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }

    public String getBankName() {
        return bankName;
    }

}
