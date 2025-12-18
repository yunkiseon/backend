package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.log4j.Log4j2;

// 시큐리티 설정 클래스

@EnableWebSecurity// 모든 웹 요청에 대해서 Security Filter Chain 을 적용시킨다
@Configuration// 스프링 설정 클래스
@Log4j2
public class SecurityConfig{
    
    @Bean 
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // 모두 막힌 상황이라면 temple 도 제대로 로딩 되지 않는다. 그래서 이 부분은 풀어줄 필요가 있다.
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/","/assets/**").permitAll()
            .requestMatchers("/sample/member").hasRole("MEMBER")
            .requestMatchers("/sample/admin").hasRole("ADMIN")
        )
        // .httpBasic(Customizer.withDefaults()) //http dafault인 로그인 창
        .formLogin(login -> login.loginPage("/member/login").defaultSuccessUrl("/", true).permitAll())
        .logout(logout->logout.logoutUrl("/member/logout")// 로그아웃도 post 처리
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        );
        
        // .formLogin(Customizer.withDefaults()); 기본 창 뜨게함
        
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        // 운영, 실무, 다중 알고리즘 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // 연습, 단일 알고리즘 사용
        // return new BCryptPasswordEncoder();
    }
    
}
