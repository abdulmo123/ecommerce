package com.abdulmo123.ecommerce.config;

import com.abdulmo123.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.security.cert.CertPathBuilder;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    public AuthenticationManagerBuilder authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        return auth.authenticationProvider(authenticationProvider());
    }

    /*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                    .configurationSource(request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
                        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                        config.setAllowedHeaders(Arrays.asList("*"));
                        return config;
                    })
                .and()
                .authorizeHttpRequests()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/home", true)
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll();

        http.csrf().disable();

        return http.build();
    }
}
