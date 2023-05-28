package com.example.bezbednostbackend.config;

import com.example.bezbednostbackend.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//configures all http security of app
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class SecurityConfiguration {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider myAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
        var auth1 = AuthorityAuthorizationManager.<RequestAuthorizationContext>hasRole("USER");
        //auth1.setRoleHierarchy(roleHierarchy());

        http
                .csrf()
                .disable()
                //whitelisting pages
                .authorizeHttpRequests()

                .requestMatchers("/auth/*").permitAll()
                // role hierarchy
                //.requestMatchers(HttpMethod.GET).access(auth1)
                .anyRequest()
                .authenticated()
                //decision management - if user authorized do not store session state
                //ensures that each request is authenticated
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(myAuthenticationProvider)
                //use filter before UsernamePasswordFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


   /* @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ADMIN >  PROJECT_MANAGER\n ADMIN > HR_MANAGER\n ADMIN > ENGINEER \n";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }*/
}
