package com.frankmoley.boot.landon.roomwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Allow these websites without any security.
        http.authorizeRequests().antMatchers("/","/api/*").permitAll()
                //Any request outside will require authentication
                .anyRequest().authenticated()
                .and()
                //Form to fill in to login is required.
                .formLogin()
                //Redirect to the login page.
                .loginPage("/logins")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //Simulate an actual user
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
}
