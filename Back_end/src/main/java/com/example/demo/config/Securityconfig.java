package com.example.demo.config;



import com.example.demo.service.SignupService;
import com.example.demo.service.LoginSuccessService;

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
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private final SignupService signupService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override  
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/login", "/signup", "/user", "/main", "/ranking", "/signup_check", "/signupfail").permitAll() 
            .antMatchers("/user").hasRole("USER") 
            .antMatchers("/admin").hasRole("ADMIN, ROLE_USER") 
            .anyRequest().authenticated()
        
        .and()
            .formLogin()
                .loginPage("/main")
                .loginProcessingUrl("/login_check")
                .usernameParameter("user_id")
                .passwordParameter("password")
                .successHandler(new LoginSuccessService())
        .and()
            .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
        ;
    }
  
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(signupService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
}

