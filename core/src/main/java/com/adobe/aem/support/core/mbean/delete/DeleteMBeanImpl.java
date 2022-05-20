package com.adobe.aem.support.core.mbean.delete;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;

import com.adobe.aem.support.core.constants.ServiceUser;
import com.adobe.aem.support.core.mbean.delete.condition.BasicCondition;
import com.adobe.aem.support.core.mbean.delete.condition.Condition;
import com.adobe.aem.support.core.mbean.delete.condition.ConditionException;
import com.adobe.granite.jmx.annotation.AnnotatedStandardMBean;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = DynamicMBean.class, property = {
        "jmx.objectname=com.adobe.aem.support.core.mbean.delete:type=DeleteMBean"
})
public class DeleteMBeanImpl extends AnnotatedStandardMBean implements DeleteMBean {

    public DeleteMBeanImpl() throws NotCompliantMBeanException {
        super(DeleteMBean.class);
    }

    @Reference
    private SlingRepository slingRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean HAS_DELETED = false;
    private final String JCR_PRIMARY_TYPE = "jcr:primaryType";

    @Override
    public boolean delete(String path, String nodeType, String condition) {
        if (path == null || path.isEmpty()) {
            logger.error("Invalid path parameter, cannot be blank please fix this");
            return false;
        }

        if (nodeType == null || nodeType.isEmpty()) {
            logger.error("Invalid nodeType, cannot be blank please fix this");
            return false;
        }

        Session session = null;
        try {
            session = this.getSession();
        } catch (RepositoryException e) {
            logger.error("Failed to get session please make sure the {} is present", e,
                    ServiceUser.AEM_SUPPORT_SUBSERVICE);
            return false;
        }

        Node pathNode = null;
        try {
            pathNode = session.getNode(path);
        } catch (RepositoryException e) {
            logger.error("Failed to get node at the given path, please make sure the path is valid - {}", e, path);
            closeSession(session);
            return false;
        }

        Condition basicCondition = new BasicCondition();
        try {
            basicCondition.parse(condition);
        } catch (ConditionException e) {
            logger.warn("No condition has been specified");
        }

        try {
            NodeIterator nodeIterator = pathNode.getNodes();
            while (nodeIterator.hasNext()) {
                Node child = nodeIterator.nextNode();
                try {

                    String primaryType = child.getProperty(JCR_PRIMARY_TYPE).getString();
                    if (primaryType.equals(nodeType)) {
                        if (basicCondition.isNotNull()) {
                            String property = basicCondition.getProperty();
                            if (child.getProperty(property).getString().contains(basicCondition.getValue())) {
                                logger.info("Condition is met for given condition {} ", basicCondition.toString());
                                session.refresh(true);
                                child.remove();
                                session.save();
                            } else {
                                logger.info("Condition is not met for given condition {} ", basicCondition.toString());
                            }
                        } else {
                            logger.info("No condition is in the Condition object about to delete");
                            session.refresh(true);
                            child.remove();
                            session.save();
                        }
                    } else {
                        logger.info("Skipping {} as it is the wrong primary type", child.getPath());
                    }

                } catch (Exception e) {
                    logger.warn("Failed to get property for node {} skipping this node", child.getPath());
                }

            }
        } catch (RepositoryException e) {
            logger.error("Failed to get the nodes under the given path {}", e, path);
            closeSession(session);
            return false;
        }

        closeSession(session);
        return HAS_DELETED == true;
    }

    private Session getSession() throws LoginException, RepositoryException {
        return this.slingRepository.loginService(ServiceUser.AEM_SUPPORT_SUBSERVICE, null);
    }

    private void closeSession(Session session) {
        session.logout();
    }

}
