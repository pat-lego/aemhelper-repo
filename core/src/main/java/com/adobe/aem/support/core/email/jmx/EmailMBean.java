package com.adobe.aem.support.core.email.jmx;

import com.adobe.granite.jmx.annotation.Description;

@Description("MBean to send emails and test configuration")
public interface EmailMBean {
    
    @Description("Get list of recipients")
    String getRecipients();

    @Description("Set list of recipients")
    void setRecipients(String recipients);

    @Description("Get list of cc recipients")
    String getCc();

    @Description("Set list of cc recipients")
    void setCc(String cc);

    @Description("Get email subject")
    String getSubject();

    @Description("Set email subject")
    void setSubject(String subject);

    @Description("Get email from")
    String getFrom();

    @Description("Set email from")
    void setFrom(String from);

    @Description("Set email message")
    String getMsg();

    @Description("Get email message")
    void setMsg(String msg);
    
    @Description("Send with sample configurations")
    boolean send();

    @Description("Send with provided configurations")
    boolean send(String recipients, String cc, String subject, String from, String msg);
}
