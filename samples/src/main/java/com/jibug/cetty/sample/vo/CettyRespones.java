package com.jibug.cetty.sample.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CettyRespones {

    private Integer code;
    private String msg;
    private Object content;

    public CettyRespones setCode(Integer code) {
        this.code = code;
        return this;
    }

    public CettyRespones setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
        return this;
    }

    public CettyRespones setContent(Object content) {
        this.content = content;
        return this;
    }
}
