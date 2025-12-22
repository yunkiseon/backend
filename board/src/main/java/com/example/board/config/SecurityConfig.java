package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;


import com.example.board.member.handler.LoginSuccessHandler;

import lombok.extern.log4j.Log4j2;

// 시큐리티 설정 클래스

@EnableMethodSecurity// preauthorize, postauthorize 등을 위해서 필요함
@EnableWebSecurity// 모든 웹 요청에 대해서 Security Filter Chain 을 적용시킨다
@Configuration// 스프링 설정 클래스
@Log4j2
public class SecurityConfig{
    
    @Bean 
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception{
        // 모두 막힌 상황이라면 temple 도 제대로 로딩 되지 않는다. 그래서 이 부분은 풀어줄 필요가 있다.
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/","/assets/**","/js/**", "/member/auth", "/img/**", "/templates/fragments","/board/assets/images/**").permitAll()
            .requestMatchers("/member/register").permitAll()
            .requestMatchers("/board/list", "/board/read", "/board/modify","/board/remove").permitAll()
            .requestMatchers("/board/create").authenticated()
            .requestMatchers("/replies/board/**").permitAll()
            .requestMatchers("/replies/new").authenticated()
            .requestMatchers("/replies/**").authenticated()
            .requestMatchers("/board/modify/**").hasAnyRole("MANAGER","ADMIN", "USER")
            .requestMatchers("/member/profile").hasRole("USER")
            // .requestMatchers("/member/profile").authenticated() 위와 동일
            .requestMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")//role 여러개 담기 가능
            .requestMatchers("/admin/**").hasAnyRole("ADMIN")
        )
        // .httpBasic(Customizer.withDefaults()) //http dafault인 로그인 창
        .formLogin(login -> login.loginPage("/member/login").permitAll()
        // .defaultSuccessUrl("/", true).permitAll())
        .successHandler(loginSuccessHandler()))
        .oauth2Login(login -> login.successHandler(loginSuccessHandler()))// 소셜 로그인 기능
        .logout(logout->logout.logoutUrl("/member/logout")// 로그아웃도 post 처리
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        ).rememberMe(remember -> remember.rememberMeServices(rememberMeServices));
        
        // .formLogin(Customizer.withDefaults()); 기본 창 뜨게함
        
        return http.build();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService){
        // 토큰 생성용 알고리즘 
        RememberMeTokenAlgorithm eTokenAlgorithm = RememberMeTokenAlgorithm.SHA256;

        TokenBasedRememberMeServices services = new TokenBasedRememberMeServices("myKey", userDetailsService, eTokenAlgorithm);
        //브라우저에서 넘어온 remember-me 쿠키 검증용 알고리즘
        services.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
        // 7 일 정도만 유효기간
        services.setTokenValiditySeconds(60 * 60* 24 * 7);
        return services;
    }
    // 로그인이후
    @Bean
    LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        // 운영, 실무, 다중 알고리즘 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // 연습, 단일 알고리즘 사용
        // return new BCryptPasswordEncoder();
    }
    
}
