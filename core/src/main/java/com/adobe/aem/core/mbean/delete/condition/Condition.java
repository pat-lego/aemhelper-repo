package com.adobe.aem.core.mbean.delete.condition;

public interface Condition {

    public boolean isNotNull();
    
    public String getProperty();

    public String getValue();

    public void parse(String condition) throws ConditionException;
}
