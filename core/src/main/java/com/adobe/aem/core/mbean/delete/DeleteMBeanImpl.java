package com.adobe.aem.core.mbean.delete;

import javax.management.DynamicMBean;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = DynamicMBean.class, property = {
    "jmx.objectname= com.adobe.aem.core.mbean.delete:type=DeleteMBean"
})
public class DeleteMBeanImpl implements DeleteMBean {

    

    @Override
    public boolean delete(String path, String nodeType, String condition) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
