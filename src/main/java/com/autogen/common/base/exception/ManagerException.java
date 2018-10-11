package com.autogen.common.base.exception;

public abstract class ManagerException extends RuntimeException{

    public abstract void setCode(Integer code);

    public abstract Integer getCode();

    public abstract void setMessage(String message);

    public abstract String getMessage();

    public ManagerException() {
        super();
    }

    public ManagerException(String message) {
        super(message);
    }

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerException(Throwable cause) {
        super(cause);
    }

    protected ManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
