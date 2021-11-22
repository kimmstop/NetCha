package com.example.demo.config;


import com.example.demo.service.UserService;
import com.example.demo.service.loginSuccessHandler;

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
    private final UserService userService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override  
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/login", "/signup", "/user", "/mainpage", "/main", "/find").permitAll() 
            .antMatchers("/").hasRole("USER") 
            .antMatchers("/admin").hasRole("ADMIN") 
            .anyRequest().authenticated()
        
        .and()
            .formLogin() //인증 성공 후 spring security에서 설정한 기본 로그인 폼 대신 사용할 폼 지정
                .loginPage("/mainpage")
                .loginProcessingUrl("/login_check")
                .usernameParameter("user_id")
                .passwordParameter("password")
                .successHandler(new loginSuccessHandler())
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

