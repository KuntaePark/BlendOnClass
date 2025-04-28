package com.blendonclass.security;

import com.blendonclass.service.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        session.setAttribute("id", customUserDetails.getUsername());
        session.setAttribute("name", customUserDetails.getName());
        session.setAttribute("email", customUserDetails.getEmail());

        //role 체크해서 해당하는 주소로 redirect - todo
        response.sendRedirect("/");
    }
}
