package com.fmi.cardealership.config;

import com.fmi.cardealership.model.UserRole;
import com.fmi.cardealership.security.FilterChainExceptionHandlerFilter;
import com.fmi.cardealership.security.JwtAuthenticationEntryPoint;
import com.fmi.cardealership.security.JwtAuthenticationFilter;
import com.fmi.cardealership.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static org.springframework.http.HttpMethod.*;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private FilterChainExceptionHandlerFilter filterChainExceptionHandlerFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(GET, "/api/cars").permitAll()
                .antMatchers(GET, "/api/cars/**").permitAll()
                .antMatchers(PUT, "/api/cars/**").hasAnyRole(UserRole.DEALER.toString(), UserRole.ADMIN.toString())
                .antMatchers(POST, "/api/cars").hasAnyRole(UserRole.DEALER.toString(), UserRole.ADMIN.toString())
                .antMatchers(POST, "/api/payments").hasAnyRole(UserRole.DEALER.toString(), UserRole.ADMIN.toString(), UserRole.USER.toString())
                .antMatchers(GET, "/api/payments").hasAnyRole(UserRole.DEALER.toString(), UserRole.ADMIN.toString())
                .antMatchers(PUT, "/api/payments/**").hasAnyRole(UserRole.DEALER.toString(), UserRole.ADMIN.toString())
                .antMatchers(DELETE, "/api/payments/**").hasAnyRole(UserRole.DEALER.toString(), UserRole.ADMIN.toString())
                .antMatchers(DELETE, "/api/cars/**").hasAnyRole(UserRole.DEALER.toString(), UserRole.ADMIN.toString())
                .antMatchers(POST, "/api/users").hasAnyRole(UserRole.ADMIN.toString())
                .antMatchers(GET, "/api/users").hasAnyRole(UserRole.ADMIN.toString())
                .antMatchers(PUT, "/api/users/**").hasAnyRole(UserRole.ADMIN.toString())
                .antMatchers(DELETE, "/api/users/**").hasAnyRole(UserRole.ADMIN.toString())
                .antMatchers(GET, "/api/users/me").hasAnyRole(UserRole.ADMIN.toString(),
                        UserRole.USER.toString(), UserRole.DEALER.toString())
                .antMatchers("/**").permitAll()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(filterChainExceptionHandlerFilter, LogoutFilter.class);
    }

    @Bean
    public UserDetailsService getUserDetailsService(UserService userService) {
        return userService::getUserByUsername;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManager();
    }
}