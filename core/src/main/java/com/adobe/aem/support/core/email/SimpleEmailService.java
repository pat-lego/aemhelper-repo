package com.adobe.aem.support.core.email;

import java.util.List;

import com.adobe.aem.support.core.email.exceptions.EmailException;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = EmailService.class, immediate = true)
public class SimpleEmailService implements EmailService {

    private final String DEFAULT_SUBJECT = "Default Subject";
    private final String DEFAULT_MSG = "Default Message";

    @Reference
    public MessageGatewayService messageGatewayService;

    @Override
    public void sendEmail(List<String> recipients, List<String> ccRecipients, String subject, String from, String msg) throws EmailException {
        try {

            // Set up the Email message
            Email email = new SimpleEmail();

            if (recipients == null) {
                throw new EmailException("Missing mandatory recipients field, please make sure to set the recipients of this email");
            }

            for (String recipient: recipients) {
                email.addTo(recipient);
            }

            if (ccRecipients != null) {
                for (String ccRecipient : ccRecipients) {
                    email.addCc(ccRecipient);
                }
            }

            if (null != subject && !subject.isEmpty()) {
                email.setSubject(subject);
            } else {
                email.setSubject(DEFAULT_SUBJECT);
            }

            // If blank then will use the from defined in the Day CQ Mail Service
            if (null != from && !from.isEmpty()) {
                email.setFrom(from);
            }
           
            if (null != msg && !msg.isEmpty()) {
                email.setMsg(msg);
            } else {
                email.setMsg(DEFAULT_MSG);
            }


            MessageGateway<Email> messageGateway = this.messageGatewayService.getGateway(Email.class);
            messageGateway.send(email);
        } catch (org.apache.commons.mail.EmailException e) {
            throw new EmailException(e.getMessage(), e);
        }
    }

}
