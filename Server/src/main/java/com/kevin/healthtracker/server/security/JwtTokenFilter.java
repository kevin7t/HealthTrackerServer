package com.kevin.healthtracker.server.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (!token.isEmpty() && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //Gets the token and the username from the token from the user input
        //Then adds it to the request attribute which goes to the controller
        //This allows the username to be tied to the token that is used
        ((HttpServletRequest) request).getSession().setAttribute("token", token);
        ((HttpServletRequest) request).getSession().setAttribute("username", jwtTokenProvider.getUsername(token));
        chain.doFilter(request, response);
    }
}