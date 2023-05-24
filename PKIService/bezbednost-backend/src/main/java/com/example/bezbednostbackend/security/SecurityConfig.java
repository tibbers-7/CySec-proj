package com.example.bezbednostbackend.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityConfig extends WebSecurityConfigurerAdapter{
    // ovo se zovu beans btw
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthentificationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // default je sa cookies
        //super.configure(http);

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        // da ne bi login bio putanja localhost:8080/login
        // nego localhost:8080/api/login
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        // disable cross site request forgery
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        //ovk ne treba
        //http.authorizeRequests().anyRequest().permitAll();

        // ako zelimo da dozvolimo neke paths svima moramo pre ovih sledecih linija
        http.authorizeRequest().antMatchers("/api/login/**","/api/token/refresh/**").permitAll();
        http.authorizeRequest().antMatchers("/api/register/**").permitAll();


        // OVDE MENJATI
        http.authorizeRequests().anyRequest(GET,"/api/user/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().anyRequest(GET,"/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");

        // all users should be authenticated
        http.authorizeRequests().anyRequest().authenticated();

        //check the user when logging in
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter (), UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
