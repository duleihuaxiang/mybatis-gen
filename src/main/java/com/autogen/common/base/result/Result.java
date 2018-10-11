package com.autogen.common.base.result;

import java.util.List;


public class Result<T> {
    private String code;
    private String message;
    private List<String> cause;

    private T data;

    /**
     * Only used when 202 Created
     */
    private String location;


    public Result(String code, String message) {
        this(code, message, null);
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultCode resultCode) {
        this(resultCode, null);
    }

    public Result(ResultCode resultCode, T data) {
        this(resultCode.getCode(), resultCode.getMessage(), data);
    }
    
    public void setResult(String code, String message){
    	this.code = code;
    	this.message = message;
    }

    public List<String> getCause() {
        return cause;
    }

    public void setCause(List<String> cause) {
        this.cause = cause;
    }

    public void setResult(ResultCode resultCode){
    	this.code = resultCode.getCode();
    	this.message = resultCode.getMessage();
    }
    public void setResult(ResultCode resultCode, T data){
    	this.code = resultCode.getCode();
    	this.message = resultCode.getMessage();
    	this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
