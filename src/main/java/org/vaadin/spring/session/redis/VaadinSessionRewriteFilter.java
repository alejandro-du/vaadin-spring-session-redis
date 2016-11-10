package org.vaadin.spring.session.redis;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Alejandro Duarte.
 */
@WebFilter
public class VaadinSessionRewriteFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (!httpServletRequest.getRequestURI().contains("/VAADIN")) {
                HttpSession session = httpServletRequest.getSession(false);

                if (session != null) {
                    String vaadinSessionKey = "com.vaadin.server.VaadinSession.springVaadinServlet";
                    Object attributeSession = session.getAttribute(vaadinSessionKey);
                    session.setAttribute(vaadinSessionKey, attributeSession);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
    }

}
