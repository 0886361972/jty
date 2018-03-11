package com.tianyu.jty.exception;

import java.io.Serializable;

public class CaipiaoException extends RuntimeException {


    private static final long serialVersionUID = 4056054200669080790L;

    public CaipiaoException(String message){
        super(message);
    }

    public CaipiaoException(Throwable cause){
        super(cause);
    }

    public CaipiaoException(String message,Throwable cause){
        super(message,cause);
    }
}
