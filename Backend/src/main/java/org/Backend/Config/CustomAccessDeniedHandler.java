package org.Backend.Config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {
    private final String HOME_PAGE = "/index.html";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null) {
        	
        	if (auth instanceof AnonymousAuthenticationToken) {
                response.sendRedirect("/#/login");
                super.handle(request, response, e);
                return;
            }
        	
        	if (auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_EMPLOYEE") || auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN") ) {
        		 response.sendRedirect("/dashboard/Dashboard.xhtml");
                 super.handle(request, response, e);
                 return;
        	} 
    
        	 response.sendRedirect("/#/dashboard/user");
        
        }
        

        super.handle(request, response, e);
    }
}