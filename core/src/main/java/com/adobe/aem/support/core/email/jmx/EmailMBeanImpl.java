package com.adobe.aem.support.core.email.jmx;

import java.util.Arrays;
import java.util.List;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;

import com.adobe.aem.support.core.email.EmailService;
import com.adobe.aem.support.core.email.exceptions.EmailException;
import com.adobe.granite.jmx.annotation.AnnotatedStandardMBean;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = DynamicMBean.class, property = {
        "jmx.objectname=com.adobe.aem.core.email.jmx:type=EmailService"
})
public class EmailMBeanImpl extends AnnotatedStandardMBean implements EmailMBean {

    private final String DEFAULT_DELIMETER = ",";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String recipients;
    private String cc;
    private String subject;
    private String from;
    private String message;

    @Reference
    private EmailService emailService;

    public EmailMBeanImpl() throws NotCompliantMBeanException {
        super(EmailMBean.class);
    }

    public String getRecipients() {
        if (null == this.recipients) {
            return "sample_email_1@adobe.com, sample_email_2@adobe.com";
        } else {
            return this.recipients;
        }
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    @Override
    public String getCc() {
        if (null == this.cc) {
            return "sample_ccemail_1@adobe.com, sample_ccemail_2@adobe.com";            
        } else {
            return this.cc;
        }

    }

    @Override
    public void setCc(String cc) {
       this.cc = cc; 
    }

    @Override
    public String getSubject() {
        if (null == this.subject) {
            return "Sample Subject";
        } else {
            return this.subject;
        }
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getFrom() {
        if (null == this.from) {
            return "plegault@adobe.com";
        } else {
            return this.from;
        }

    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getMsg() {
        if (null == this.message) {
            return "Sample msg";
        } else {
            return this.message;
        }

    }

    @Override
    public void setMsg(String msg) {
       this.message = msg;
    }

    @Override
    public boolean send() {
        try {
            this.emailService.sendEmail(this.convertStringToList(this.getRecipients(), DEFAULT_DELIMETER),
                    this.convertStringToList(this.getCc(), DEFAULT_DELIMETER), this.getSubject(), this.getFrom(), this.getMsg());
            return Boolean.TRUE;
        } catch (EmailException e) {
            logger.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean send(String recipients, String cc, String subject, String from, String msg) {
        try {
            this.emailService.sendEmail(this.convertStringToList(recipients, DEFAULT_DELIMETER),
                    this.convertStringToList(cc, DEFAULT_DELIMETER), subject, from, msg);
            return Boolean.TRUE;
        } catch (EmailException e) {
            logger.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    protected List<String> convertStringToList(String list, String delimiter) {
        if (null == delimiter) {
            delimiter = ",";
        }

        if (null == list || list.isEmpty()) {
            return Arrays.asList();
        }

        return Arrays.asList(list.split(delimiter));
    }

}
