package com.example.studentmanagementsystem.config;

import com.example.studentmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**").permitAll();


        http.authorizeRequests()
                .antMatchers("/admin-panel/")
                .hasRole("ADMIN");

        http.authorizeRequests()
                .antMatchers("/my-students/")
                .hasRole("TEACHER");

        http.authorizeRequests()
                .antMatchers("/my-courses/")
                .hasRole("STUDENT");

        http.formLogin().loginPage("/login").permitAll()
                .loginProcessingUrl("/auth").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .successForwardUrl("/")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error");

                http.csrf().disable();

        http.logout().
                logoutSuccessUrl("/logout").permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service);
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }
}