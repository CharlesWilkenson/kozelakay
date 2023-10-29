package com.management.system.entities;

public enum Authority {
    ADMIN("user"), MEMBER("member");
    private final String value;

    Authority(String value) {
        this.value = value;
    }
}
