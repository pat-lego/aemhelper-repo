package com.adobe.aem.support.core.mbean.delete.condition;

public class ConditionException extends Exception {

    public ConditionException(String msg, Throwable t) {
        super(msg,t);
    }

    public ConditionException(String msg) {
        super(msg);
    }
    
}
