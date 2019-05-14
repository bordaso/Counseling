package org.Backend.Config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private HttpServletResponse responseOuter;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
    	
    	 responseOuter=response;
    	
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }
       
        

        redirectStrategy.sendRedirect(request, responseOuter, targetUrl);
    }

    /*
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    protected String determineTargetUrl(Authentication authentication) throws IOException {
        String url = "";
        PrintWriter out = responseOuter.getWriter();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        
        if (isEmployee(roles)) {        	
        	url = "/dashboard/emp.html";
        	out.print("\"C2\"");
        	out.flush();
        	
        } else if (isPatient(roles)) {
            url = "/dashboard/usr.html";
            out.print("\"C1\"");
        	out.flush();
        } else {
            url = "/p401.html";
        }

        return url;
    }

    private boolean isEmployee(List<String> roles) {
        if (roles.contains("ROLE_EMPLOYEE")) {
            return true;
        }
        return false;
    }

    private boolean isPatient(List<String> roles) {
        if (roles.contains("ROLE_PATIENT")) {
            return true;
        }
        return false;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}