package com.abdulmo123.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("john")
                .password(passwordEncoder().encode(""))
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("joe")
                .password(passwordEncoder().encode(""))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);

    }
    /*
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("{bcrypt}password")
                        .roles("USER")
                        .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("")
                .roles("ADMIN")
                .build();*//*

        return new InMemoryUserDetailsManager(user);
    }
    */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll();
//                .and().csrf().disable();
        /*http.authorizeRequests().requestMatchers("/home**").hasRole("USER")
                .requestMatchers("/welcome**").permitAll()
                .requestMatchers("/login**").permitAll()
                .and()
                .formLogin();
        http.csrf().disable();*/

        return http.build();
    }
}
