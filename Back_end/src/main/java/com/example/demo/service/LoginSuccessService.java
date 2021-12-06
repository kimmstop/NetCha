package com.example.demo.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessService implements AuthenticationSuccessHandler {
    public boolean isAdminUser(Authentication requestAuthentication) {
        if(requestAuthentication.getAuthorities().toString().equals("[ROLE_ADMIN, ROLE_USER]"))
            return true;
        else
            return false;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("greet", authentication.getName());
        if(isAdminUser(authentication)){
            response.sendRedirect("/admin");
        }
        else
            response.sendRedirect("/user");
        
    }   
}
