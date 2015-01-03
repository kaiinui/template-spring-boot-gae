package com.kaiinui.appenginetest.error;

/**
 * Created by kaiinui on 2015/01/01.
 */
public class ErrorResponse {
    public int code;
    public String message;
    public String url;

    public ErrorResponse() {}

    public ErrorResponse(int code, String message, String url) {
        this.code = code;
        this.message = message;
        this.url = url;
    }
}
