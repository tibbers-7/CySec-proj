package com.example.bezbednostbackend.security;

import com.example.bezbednostbackend.filters.CustomAuthorizationFilter;
import com.example.bezbednostbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import com.example.bezbednostbackend.filters.CustomAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{
    // ovo se zovu beans btw
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/js/**", "/images/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        // da ne bi login bio putanja localhost:8080/login
        // nego localhost:8080/api/login
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        // disable cross site request forgery
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        //ovk ne treba
        //http.authorizeRequests().anyRequest().permitAll();

        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/vertretungsplan").hasAnyRole("SCHUELER", "LEHRER", "VERWALTUNG")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll());
//
//        // ako zelimo da dozvolimo neke paths svima moramo pre ovih sledecih linija
//        http.authorizeRequest().antMatchers("/api/login/**","/api/token/refresh/**").permitAll();
//        http.authorizeRequest().antMatchers("/api/register/**").permitAll();
//
//
//        // OVDE MENJATI
//        http.authorizeRequests().anyRequest(GET,"/api/user/**").hasAnyAuthority("ROLE_USER");
//        http.authorizeRequests().anyRequest(GET,"/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");

        // all users should be authenticated
        http.authorizeRequests().anyRequest().authenticated();

        //check the user when logging in
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
