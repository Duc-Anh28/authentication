package com.example.authentication.type;

import com.example.authentication.util.CommonUtil;

import java.util.Objects;

public enum OrderByType {
    ASC("asc"),
    DESC("desc");

    private final String name;

    public String getName() {
        return name;
    }

    OrderByType(String name) {
        this.name = name;
    }

    public static OrderByType getInstance(String type) {
        if (CommonUtil.isNullOrEmpty(type)) return DESC;
        for (var value : OrderByType.values()) {
            if (Objects.equals(value.getName(), type.trim())) {
                return value;
            }
        }
        return DESC;
    }
}
