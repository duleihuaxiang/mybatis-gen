package com.autogen.common.base.exception;


import com.autogen.common.base.enums.EnumType;

public class BeanCopyException extends ManagerException {

    private static final long serialVersionUID = -1109101399703220536L;

    private Integer code;
    private String message;

    public BeanCopyException(Exception ex){}

    public BeanCopyException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BeanCopyException(EnumType enumType){
        this.code = enumType.code();
        this.message = enumType.text();
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
