package com.jibug.cetty.sample.constants;

public enum UrlTypeEnum {

    TYPE(1, "TYPE"),
    GOODS_LIST(2, "GOODSLIST"),
    GOODS(3,"GOODS");

    final int code;
    final String message;

    private UrlTypeEnum(int code, String message) {
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
