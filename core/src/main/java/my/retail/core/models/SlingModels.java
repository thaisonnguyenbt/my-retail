package my.retail.core.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.crypto.CryptoException;
import com.adobe.granite.crypto.CryptoSupport;
import com.day.cq.replication.Agent;
import com.day.cq.replication.AgentFilter;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.Gson;

@SlingServlet(paths="/bin/slingmodel", methods="GET")
public class SlingModels extends SlingAllMethodsServlet{

	private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(SlingModels.class);
    
    
    @Reference
    ResourceResolverFactory resourceResolverFactory;    
    
    @Reference
    private CryptoSupport cryptoSupport;
    
    @Reference
    private Replicator replicator;
    
    ResourceResolver resourceResolver;
    
    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)throws ServletException,IOException{
        logger.info("inside sling model test servlet");
        response.setContentType("text/html");
        try {
            resourceResolver = request.getResourceResolver();
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            
            /**
             * get Pages
             */
            Page pages = pageManager.getPage("/content/my-retail");
            
            Iterator<Page> listChildren = pages.listChildren();
            while (listChildren.hasNext()) {
            	Page page = listChildren.next();
            	response.getWriter().write(page.getTitle() + " : " + page.getName() + " : " + page.getPath() + "<br/>");
            }
            response.getWriter().write("<br/>");
            Resource resource = resourceResolver.getResource("/content/my-retail/jcr:content");
            ValueMap valueMap = resource.adaptTo(ValueMap.class);
             
            /**
             * get attributes
             */
            response.getWriter().write(new Gson().toJson(valueMap));
            response.getWriter().write("<br/><br/>");

            response.getWriter().write(new Gson().toJson(resource.getResourceType()));
            response.getWriter().write("<br/>");
               
            /**
             * add node
             */
            Session session = resourceResolver.adaptTo(Session.class);
            Node node = session.getNode("/content/my-retail/jcr:content");
            if (!node.hasNode("somenode")) {
                final Node somenode = node.addNode("somenode");
                somenode.setProperty("myproperty", "my property value");
            }
            
            /**
             * replicate content
             */
            replicationConent("/content/my-retail/jcr:content", session);
            
            session.save();
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally{
            if(resourceResolver.isLive())
                resourceResolver.close();
        }
    }
    
    //Replicate the playload using the Replication API
    private String replicationConent(String path, Session session) {
	    try {
	              
	        // Create leanest replication options for activation
	        ReplicationOptions options = new ReplicationOptions();
	        // Do not create new versions as this adds to overhead
	        options.setSuppressVersions(true);
	        // Avoid sling job overhead by forcing synchronous. Note this will result in serial activation.
	        options.setSynchronous(true);
	        // Do NOT suppress status update of resource (set replication properties accordingly)
	        options.setSuppressStatusUpdate(false);  

            AgentFilter agentFilter = new AgentFilter() {

                @Override
                public boolean isIncluded(Agent arg0) {

                    if ("custom-agent".equals(arg0.getId())) {
                        return true;
                    }
                    return false;
                }
            };
            options.setFilter(agentFilter);
	     
	        logger.info("**** ABOUT TO REPLICATE" ) ;  
	        //Rep the content      replicate(Session session, ReplicationActionType type, String path)
            replicator.replicate(session, ReplicationActionType.ACTIVATE, path, options);

	        logger.info("**** REPLICATED" ) ;  
	         
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        logger.info("**** Error: " +e.getMessage()) ;  
	    }
	    return null;
    }

    
    public ResourceResolver getUserResourceResolver() throws LoginException {
        Map<String,Object> authenticationInfo = new HashMap<String, Object>(2);
        authenticationInfo.put(ResourceResolverFactory.USER, "admin");
        String unprotectedPass;
        try {
            unprotectedPass = cryptoSupport.unprotect("admin");
        } catch (CryptoException e) {
            unprotectedPass = "admin";
            logger.error(e.getMessage());
        }
        authenticationInfo.put(ResourceResolverFactory.PASSWORD, unprotectedPass.toCharArray());
        return resourceResolverFactory.getResourceResolver(authenticationInfo);
    }
}
