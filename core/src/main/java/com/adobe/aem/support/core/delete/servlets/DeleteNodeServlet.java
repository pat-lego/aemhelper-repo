package com.adobe.aem.support.core.delete.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.adobe.aem.support.core.constants.ServiceUser;
import com.google.gson.JsonObject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.jcr.JsonItemWriter;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "support/pages/delete", methods = "GET", extensions = "json")
public class DeleteNodeServlet extends SlingAllMethodsServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    @SuppressWarnings("deprecation")
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException, ServletException {

        String suffix = request.getRequestPathInfo().getSuffix();

        if (suffix == null || suffix.isEmpty()) {
            logger.error(
                    "A suffix is required for this URL to be able to read the node definition, please make sure to add one to this request");
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try (ResourceResolver resourceResolver = this.resourceResolverFactory
                .getServiceResourceResolver(ServiceUser.getAEMSupportAuthMap());
                PrintWriter out = response.getWriter()) {
            Resource resource = resourceResolver.getResource(suffix);
            Node node = resource.adaptTo(Node.class);

            JsonItemWriter jsonItemWriter = new JsonItemWriter(null);
            jsonItemWriter.dump(node, response.getWriter(), 1, true);

            response.setStatus(SlingHttpServletResponse.SC_OK);
            response.setContentType("application/json");
            out.flush();
            out.close();
        } catch (LoginException | JSONException | RepositoryException e) {
            logger.error(e.getMessage(), e);
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }

}
