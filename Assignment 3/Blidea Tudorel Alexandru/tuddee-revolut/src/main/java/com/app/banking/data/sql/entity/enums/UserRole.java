package com.app.banking.data.sql.entity.enums;

import java.util.List;

public enum UserRole {
    ADMIN, CUSTOMER, BUSINESS, ANALYST, BANKER;
    public static final List<UserRole> ALL_ROLES = List.of(ADMIN, CUSTOMER, BUSINESS, ANALYST, BANKER);

    public static List<UserRole> getListOfAllRoles() {
        return ALL_ROLES;
    }

}
