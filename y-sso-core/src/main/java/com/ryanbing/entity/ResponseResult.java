package com.ryanbing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author ryan
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    public ResponseResult() {
    }

    private ResponseResult(int status) {
        this.status = status;
    }

    private ResponseResult(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ResponseResult(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ResponseResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    public static class ResponseBuilder<T> {
        private int status;
        private String msg;
        private T data;

        ResponseBuilder() {
        }

        public ResponseBuilder<T> status(int status) {

            this.status = status;

            return this;
        }

        public ResponseBuilder<T> message(String msg) {
            if (msg != null) {
                this.msg = msg;
            }
            return this;
        }

        public ResponseBuilder<T> data(T data) {
            if (data != null) {
                this.data = data;
            }
            return this;
        }

        public ResponseResult<T> build() {
            return new ResponseResult<>(status, msg, data);
        }
    }


    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ResponseResult<T> success(String msg) {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ResponseResult<T> success(String msg, T data) {
        return new ResponseResult<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }


    public static <T> ResponseResult<T> success(int code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    public static <T> ResponseResult<T> entity(int status, String msg, T data) {
        return new ResponseResult<>(status, msg, data);
    }

    public static <T> ResponseResult<T> error() {
        return new ResponseResult<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ResponseResult<T> error(String msg) {
        return new ResponseResult<>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> ResponseResult<T> error(int code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
