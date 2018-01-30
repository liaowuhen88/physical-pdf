package com.bean;

import java.io.Serializable;

/**
 * Created by yutao on 2015/7/31.
 */
public class Response<T> implements Serializable {
    private boolean success;
    private int code;
    private String msg;
    private T data;

    public Response() {

    }

    public Response(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Response(String msg, boolean success, int code) {
        this.msg = msg;
        this.success = success;
        this.code = code;
    }

    public Response(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
