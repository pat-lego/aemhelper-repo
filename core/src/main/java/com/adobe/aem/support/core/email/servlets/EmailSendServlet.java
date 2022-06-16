package com.adobe.aem.support.core.email.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.adobe.aem.support.core.email.EmailService;
import com.adobe.aem.support.core.email.exceptions.EmailException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "support/pages/email", methods = "POST", extensions = "json")
public class EmailSendServlet extends SlingAllMethodsServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference
    private EmailService emailService;

    private static final String RECIPIENTS = "recipients";
    private static final String CCRECIPIENTS = "ccRecipients";
    private static final String SUBJECT = "subject";
    private static final String FROM = "from";
    private static final String MSG = "msg";

    private final Gson gson = new Gson();

    @Override
    @SuppressWarnings("deprecation")
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException, ServletException {
        try (PrintWriter writer = response.getWriter()) {

            JsonObject inputJson = new JsonParser()
                    .parse(IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8)).getAsJsonObject();
            String msg = inputJson.get(MSG).getAsString();
            
            String from = null;
            if (inputJson.get(FROM) != null) {
                from = inputJson.get(FROM).getAsString();
            }
            
            String subject = inputJson.get(SUBJECT).getAsString();

            List<String> recipients = getListFromJsonArray(inputJson.get(RECIPIENTS).getAsJsonArray(),
                    new TypeToken<List<String>>() {
                    });
            List<String> ccRecipients = null;
            if (inputJson.get(CCRECIPIENTS) != null) {
                ccRecipients = getListFromJsonArray(inputJson.get(CCRECIPIENTS).getAsJsonArray(),
                        new TypeToken<List<String>>() {
                        });
            }

            this.emailService.sendEmail(recipients, ccRecipients, subject, from, msg);

            JsonObject resultJson = new JsonObject();
            resultJson.addProperty("status", "success");
            resultJson.add("input", inputJson);

            response.setContentType("application/json");
            response.setStatus(SlingHttpServletResponse.SC_OK);
            writer.write(resultJson.toString());
            writer.flush();

        } catch (EmailException e) {
            logger.error(e.getMessage(), e);
            response.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

    }

    public <T> List<T> getListFromJsonArray(JsonArray array, TypeToken<List<T>> typeToken) {
        return this.gson.fromJson(array, typeToken.getType());
    }

}
