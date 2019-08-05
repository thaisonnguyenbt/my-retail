package my.retail.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseSightlyModel {

    private static Logger LOG = LoggerFactory.getLogger(BaseSightlyModel.class);

    @Inject
    protected transient SlingHttpServletRequest request;

    @Inject
    protected transient SlingHttpServletResponse response;

    /**
     * abstract function to do logic business code in derived classes
     * 
     * @throws Exception
     */
    protected abstract void init() throws Exception;

    @PostConstruct
    protected void doInit() {

        try {
            this.doInit();
        } catch (Exception e) {
            if (HttpServletResponse.class.isAssignableFrom(response.getClass())) {
                String requestURI = request.getRequestURI();
                LOG.error("ERROR_SIGHTLY happened {} - {}", requestURI, getFirstLinesOfStacktrace(e, 10));
                response.setStatus(500);
            }
        }
    }

    private String getFirstLinesOfStacktrace(Exception e, int line) {

        StackTraceElement[] stack = e.getStackTrace();
        String result = "";
        for (int i = 0; i < line && i < stack.length; i++) {
            result += "\n" + String.format("%-120s", stack[i]);
        }
        return result;
    }
}
