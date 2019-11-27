package com.jibug.cetty.sample.exception;

/**
 * Argument Requirement Exception
 */
public class TaskException extends RuntimeException {

    private int code;

    public TaskException() {
    }

    public TaskException(String message) {
        super(message);
    }

    public TaskException(Throwable cause) {
        super(cause);
    }

    public TaskException(int code, String message) {
        super(message);
        this.code = code;
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public TaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public int getCode() {
        return code;
    }


    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code + "" +
                ", \"message\":\"" + getMessage() + "\"" +
                "}";
    }
}
