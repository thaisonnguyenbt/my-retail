package my.retail.core.servlets;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(metatype = true)
@Service
public class Flushcache extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = -3415432102245414091L;

    @Property(value = "/bin/flushcache")
    static final String SERVLET_PATH = "sling.servlet.paths";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

        try {
            // retrieve the request parameters
            String handle = request.getParameter("handle");
            String page = request.getParameter("page");

            // hard-coding connection properties is a bad practice, but is done here to simplify the example
            String server = "localhost";
            String uri = "/dispatcher/invalidate.cache";

            HttpClient client = new HttpClient();

            PostMethod post = new PostMethod("http://" + server + uri);
            post.setRequestHeader("CQ-Action", "Activate");
            post.setRequestHeader("CQ-Handle", handle);

            StringRequestEntity body = new StringRequestEntity(page, null, null);
            post.setRequestEntity(body);
            post.setRequestHeader("Content-length", String.valueOf(body.getContentLength()));
            client.executeMethod(post);
            post.releaseConnection();
            // log the results
            logger.info("result: " + post.getResponseBodyAsString());
        } catch (

        Exception e) {
            logger.error("Flushcache servlet exception: " + e.getMessage());
        }
    }
}