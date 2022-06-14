package com.adobe.aem.support.core.email.exceptions;

public class EmailException extends Exception {

    public EmailException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailException(String msg) {
        super(msg);
    }
    
}
