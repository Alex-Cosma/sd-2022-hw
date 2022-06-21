package com.app.banking.data.mongo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserHistory {

    private String purpose;
    private Map<String, String> fieldsHistory = new HashMap<>();

}
