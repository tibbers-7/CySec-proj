package com.example.bezbednostbackend.config;

import com.example.bezbednostbackend.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

//configures all http security of app
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {


    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userService;


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .sameOrigin()
                        ).xssProtection())
                .csrf().disable()
                //whitelisting pages
                .authorizeHttpRequests()
                .requestMatchers("/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                //decision management - if user authorized do not store session state
                //ensures that each request is authenticated
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().and()
                .authenticationProvider(authenticationProvider())
                //use filter before UsernamePasswordFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public KeyStore keyStore() throws IOException, NoSuchAlgorithmException, java.security.cert.CertificateException, KeyStoreException {
//        String keystorePath = "enc.jks";
//        String keystorePassword = "3gfzZDw6hL+%qpQ8YrUKVEB#K";
//
//        ClassPathResource keystoreResource = new ClassPathResource(keystorePath);
//        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        keyStore.load(keystoreResource.getInputStream(), keystorePassword.toCharArray());
//
//        return keyStore;
//    }
//
//    @Bean
//    public TextEncryptor textEncryptor(KeyStore keyStore) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeySpecException, UnrecoverableEntryException, KeyStoreException {
//        String keyAlias = "bezbednost-encKey";
//        String keyPassword = "3gfzZDw6hL+%qpQ8YrUKVEB#K";
//        String salt = "5:XEctyu))7mKo*4ewA.Hl-J2"; // Generate a unique salt value for your application
//
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//        KeyStore.PasswordProtection keyPasswordProtection = new KeyStore.PasswordProtection(keyPassword.toCharArray());
//        KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(keyAlias, keyPasswordProtection);
//        PBEKeySpec pbeKeySpec = new PBEKeySpec(salt.toCharArray(), secretKeyEntry.getSecretKey().getEncoded(), 1000, 256);
//        SecretKey secretKey = keyFactory.generateSecret(pbeKeySpec);
//
//        TextEncryptor encryptor = Encryptors.text(new String(secretKey.getEncoded()), salt);
//        return encryptor;
//    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }
}
