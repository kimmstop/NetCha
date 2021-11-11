package com.example.demo.config;


import com.example.demo.service.UserService;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class Securityconfig extends WebSecurityConfigurerAdapter{
    private final UserService userService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/h2-console/**");
    }

    @Override  
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/login", "/signup", "/user").permitAll() 
            .antMatchers("/").hasRole("USER") 
            .antMatchers("/admin").hasRole("ADMIN") 
            .anyRequest().authenticated()
        
        .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
        .and()
            .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
      
        ;
    }
  
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
}

