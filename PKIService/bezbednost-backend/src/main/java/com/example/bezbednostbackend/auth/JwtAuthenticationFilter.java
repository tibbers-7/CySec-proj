package com.example.bezbednostbackend.auth;

import com.example.bezbednostbackend.auth.JwtService;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
                final String authHeader=request.getHeader("Authorization");
                final String jwt;
                final String username;

                if(authHeader == null ||!authHeader.startsWith("Bearer ")){
                    filterChain.doFilter(request,response);
                    return;
                }

                jwt=authHeader.substring(7);

                //TODO extract userEmail from JWT token
                username=jwtService.extractUsername(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication()==null ){
                    UserDetails userDetails = this.userService.loadUserByUsername(username);
                    if(jwtService.isTokenValid(jwt,userDetails)){
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                    filterChain.doFilter(request,response);
                }
            }


}
