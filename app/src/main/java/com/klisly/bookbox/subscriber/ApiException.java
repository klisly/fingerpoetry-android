/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.klisly.bookbox.subscriber;
public class ApiException extends Exception {

    private int code;
    private String message;

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public void setCode(int code){
        this.code = code;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String msg) {
        this.message = msg;
    }
}