package com.adobe.aem.support.core.email.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "support/pages/email", methods = "POST", extensions = "json")
public class EmailSendServlet extends SlingAllMethodsServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
        
        JsonObject jsonObject = new JsonParser().parse(IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8)).getAsJsonObject();
        String msg = jsonObject.get(MSG).getAsString();
        String from = jsonObject.get(FROM).getAsString();
        String subject = jsonObject.get(SUBJECT).getAsString();

        List<String> recipients = getListFromJsonArray(jsonObject.get(RECIPIENTS).getAsJsonArray(), new TypeToken<List<String>>(){});
    }

    public <T> List<T> getListFromJsonArray(JsonArray array, TypeToken<List<T>> typeToken) {
        return this.gson.fromJson(array, typeToken.getType());
    }

}
