package com.example.bezbednostbackend.config;

import com.example.bezbednostbackend.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//configures all http security of app
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider myAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                //whitelisting pages
                .authorizeHttpRequests()
                .requestMatchers("/auth/**","user/**")
                .permitAll()
                //dozvolila sam ove metode zbog testiranja, kad se namesti po rolama izbrisacu
                .requestMatchers("/address/*").permitAll()
                .requestMatchers("/user/*").permitAll()
                .requestMatchers("/user/findById/*").permitAll()
                .requestMatchers("/project/*").permitAll()
                .requestMatchers("/project/findByProjectManager/*").permitAll()
                .requestMatchers("/projectWork/*").permitAll()
                .requestMatchers("/projectWork/findByProjectID/*").permitAll()
                .requestMatchers("/projectWork/findByEngineerID/*").permitAll()
                .requestMatchers("/skill/*").permitAll()
                .requestMatchers("/skill/findByEngineerID/*").permitAll()
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
}
