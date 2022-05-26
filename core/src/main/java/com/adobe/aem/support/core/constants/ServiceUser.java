package com.adobe.aem.support.core.constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.ResourceResolverFactory;

public class ServiceUser {

    public ServiceUser() {

    }


    public static final String AEM_SUPPORT_SUBSERVICE = "aemsupport";
    
    public static final Map getAEMSupportAuthMap() {
        Map auth = new HashMap<Object, Object>();
        auth.put(ResourceResolverFactory.SUBSERVICE, AEM_SUPPORT_SUBSERVICE);
        return auth;
    }
}
