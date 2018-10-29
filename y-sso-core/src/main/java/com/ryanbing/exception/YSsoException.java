package com.ryanbing.exception;

/**
 * @author ryan
 **/
public class YSsoException extends RuntimeException{

    private static final long serialVersionUID = -6694086071798794393L;

    public YSsoException(String msg) {
        super(msg);
    }

    public YSsoException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public YSsoException(Throwable cause) {
        super(cause);
    }
}
