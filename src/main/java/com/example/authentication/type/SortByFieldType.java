package com.example.authentication.type;

import com.example.authentication.util.CommonUtil;

import java.util.Objects;

public enum SortByFieldType {
    MODIFIED_AT("modifiedAt"),
    CREATED_AT("createdAt");
    private final String name;

    public String getName() {
        return name;
    }

    SortByFieldType(String name) {
        this.name = name;
    }

    public static SortByFieldType getInstance(String type) {
        if (CommonUtil.isNullOrEmpty(type)) return MODIFIED_AT;
        for (var value : SortByFieldType.values()) {
            if (Objects.equals(value.getName(), type.trim())) {
                return value;
            }
        }
        return MODIFIED_AT;
    }
}
