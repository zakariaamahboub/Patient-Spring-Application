package com.example.tp2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder passwordEncoder = passwordEncoder();
        System.out.println("************");
        System.out.println(passwordEncoder.encode("1234"));
        System.out.println("************");
        auth.jdbcAuthentication()
                .dataSource(dataSource) // Data source de l’application
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);
/*
        auth.inMemoryAuthentication().withUser("user1")
                .password(passwordEncoder.encode("1234")).roles("USER");

        auth.inMemoryAuthentication().withUser("user2")
                .password(passwordEncoder.encode("1234")).roles("USER");
        auth.inMemoryAuthentication().withUser("admin")
                .password(passwordEncoder.encode("1234")).roles("USER","ADMIN");*/
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin().loginPage("/login");
        http.authorizeRequests().antMatchers("/patients**/**").hasAnyRole("USER","ADMIN");


      http.authorizeRequests().antMatchers("/save**/**","/delete**/**","/form**/**").hasRole("ADMIN");

        http.authorizeRequests().antMatchers("/users/**","/login","/webjars/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/notAuthorized");



    }
    @Bean
    public PasswordEncoder passwordEncoder(){ return  new BCryptPasswordEncoder(); }
}
