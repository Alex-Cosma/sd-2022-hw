package com.app.banking.data.sql.entity.enums;

import java.util.List;

public enum EDepositType {

    FOR_CAR, FOR_HOUSE, ECONOMY, FOR_PLECAT_LA_ROMA_SAU_PARIS;

    private static final List<EDepositType> DEPOSIT_TYPES = List.of(FOR_CAR, FOR_HOUSE, ECONOMY, FOR_PLECAT_LA_ROMA_SAU_PARIS);

    public static List<EDepositType> getDepositTypes() {
        return DEPOSIT_TYPES;
    }

}
