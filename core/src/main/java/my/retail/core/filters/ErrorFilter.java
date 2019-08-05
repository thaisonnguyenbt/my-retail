package my.retail.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.engine.EngineConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Filter.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Error Filter Class",
        EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
        Constants.SERVICE_RANKING + ":Integer=0"

})
public class ErrorFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (HttpServletRequest.class.isAssignableFrom(request.getClass())) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();
            if ("XMLHttpRequest".equals(httpRequest.getHeader("X-Requested-With"))) { // only cater for ajax requests, html resources will he handled by default error.html page
                try {
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    log.error("ERROR happened when Serving request {} - {}", requestURI, getFirstLinesOfStacktrace(exception, 10));
                    ((HttpServletResponse) response).setStatus(500);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
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

    @Override
    public void destroy() {

    }

}
