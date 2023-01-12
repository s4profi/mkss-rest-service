package com.hsbremen.student.mkss.restservice.util;

public enum Status {
    EMPTY("E"), IN_PREPARATION("I"), COMMITTED("C"), ACCEPTED("A"), REJECTED("R");

    private String code;

    private Status(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
