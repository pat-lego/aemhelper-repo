package com.adobe.aem.support.core.delete.condition;

public class BasicCondition implements Condition {

    private String property;
    private String value;

    @Override
    public String getProperty() {
        return this.property;
    }

    @Override
    public String getValue() {
       return this.value;
    }

    @Override
    public void parse(String condition) throws ConditionException {
       if (condition == null) {
           return;
       }

       String[] split = condition.split("=");
       if (split.length < 2 || split.length >= 3) {
           throw new ConditionException("Missing or extra token \"=\" in the condition string");
       }

       this.property = split[0];
       this.value = split[1];   
    }

    @Override
    public boolean isNotNull() {
        return this.property != null && this.value != null;
    }

    public String toString() {
        return "Property " + this.property + " Value " + this.value;
    }
    
}
