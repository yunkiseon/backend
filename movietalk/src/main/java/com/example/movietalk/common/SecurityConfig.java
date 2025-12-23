package com.example.movietalk.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;


import lombok.extern.log4j.Log4j2;

// 시큐리티 설정 클래스

@EnableMethodSecurity// preauthorize, postauthorize 등을 위해서 필요함
@EnableWebSecurity// 모든 웹 요청에 대해서 Security Filter Chain 을 적용시킨다
@Configuration// 스프링 설정 클래스
@Log4j2
public class SecurityConfig{
    
    @Bean 
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/","/assets/**","/js/**", "/img/**").permitAll()
            .anyRequest().permitAll());                                                                                               
        // http.formLogin(login -> login
        //     .loginPage("/member/login").successHandler(loginSuccessHandler()).permitAll());
        // http.oauth2Login(login -> login.successHandler(loginSuccessHandler()));
        // http.logout(logout -> logout.logoutUrl("/member/logout").logoutSuccessUrl("/"));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        // http.rememberMe(remember -> remember.rememberMeServices(rememberMeServices));
        
        // csrf 기능 중지-> 개발 중에만
        http.csrf(csrf -> csrf.disable());
        // 특정 경로에만 csrf 중지
        // http.csrf(csrf -> csrf.ignoringRequestMatchers("/replies"));
        
        
        
        return http.build();
    }

    // @Bean
    // RememberMeServices rememberMeServices(UserDetailsService userDetailsService){
    //     // 토큰 생성용 알고리즘 
    //     RememberMeTokenAlgorithm eTokenAlgorithm = RememberMeTokenAlgorithm.SHA256;

    //     TokenBasedRememberMeServices services = new TokenBasedRememberMeServices("myKey", userDetailsService, eTokenAlgorithm);
    //     //브라우저에서 넘어온 remember-me 쿠키 검증용 알고리즘
    //     services.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
    //     // 7 일 정도만 유효기간
    //     services.setTokenValiditySeconds(60 * 60* 24 * 7);
    //     return services;
    // }


    // 로그인이후
    // @Bean
    // LoginSuccessHandler loginSuccessHandler(){
    //     return new LoginSuccessHandler();
    // }

    @Bean
    PasswordEncoder passwordEncoder(){
        // 운영, 실무, 다중 알고리즘 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // 연습, 단일 알고리즘 사용
        // return new BCryptPasswordEncoder();
    }
    
}
