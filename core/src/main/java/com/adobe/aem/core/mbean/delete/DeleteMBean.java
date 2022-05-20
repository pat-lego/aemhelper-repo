package com.adobe.aem.core.mbean.delete;

import com.adobe.granite.jmx.annotation.Description;

@Description("MBean to delete nodes under a specific location")
public interface DeleteMBean {
    
    /**
     * 
     * @param nodeType - Node to consider to delete if the nodeType is the same as the one provided in the function
     * @param condition - Condition to delete the node; propertName=string. If the property name contains the given string then it will be deleted
     * @param path - The path to iterate, does not recursively go down the tree
     * @return - True if anything was deleted False otherwise
     */
    @Description("Delete specific node types under a given path only when a specific condition is met")
    public boolean delete(String path, String nodeType, String condition);
}
