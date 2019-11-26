package com.jibug.cetty.sample.constants;

public enum AreaTypeEnum {

    MX(1, "MX"),
    BR(2, "BR");

    final int code;
    final String message;

    private AreaTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Short code() {
        return (short) this.code;
    }

    public String message() {
        return this.message;
    }
}
