package com.Daniel_Muntean4.pomodoro_api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;
import java.util.List;
import java.util.StringJoiner;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    JwtFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
             HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
    if(header!=null && header.startsWith("Bearer ") ){
        String token = header.substring("Bearer ".length());


    try{
        Claims claims = jwtService.parseClaims(token);
        List<String> permissions = claims.get("permissions", List.class);
        List<SimpleGrantedAuthority> authorities  = (permissions==null) ? List.of() :
                permissions.stream().map(SimpleGrantedAuthority::new).toList();
        var auth = new UsernamePasswordAuthenticationToken
                (claims.getSubject(),null,authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

    } catch (JwtException e) {
        SecurityContextHolder.clearContext();
    }}
    filterChain.doFilter(request,response);

    }
}
