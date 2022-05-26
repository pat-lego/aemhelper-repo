package com.adobe.aem.support.core.delete.condition;

public class ConditionException extends Exception {

    public ConditionException(String msg, Throwable t) {
        super(msg,t);
    }

    public ConditionException(String msg) {
        super(msg);
    }
    
}
