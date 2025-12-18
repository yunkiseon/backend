package com.example.security.config;

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
    
    @Bean // 객체 생성 
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // 어떤 요청이든 바로 인증해라.authorize.anyRequest().authenticated() 이 경우 무조건 로그인을 해야한다.
        // 아래의 경우, 게스트와 / 의경우 모두 가능, 멤버와 어드민은 조건이 있는 것.
        // 조건을 따로 주지 않는다면 일단 막아진다.
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/","/sample/guest").permitAll()
            .requestMatchers("/sample/member").hasRole("MEMBER")
            .requestMatchers("/sample/admin").hasRole("ADMIN")
            // .httpBasic(Customizer.withDefaults()); http dafault인 로그인 창
        )
        .formLogin(login -> login.loginPage("/sample/login").defaultSuccessUrl("/", true).permitAll())
        .logout(logout->logout.logoutUrl("/sample/logout")// 로그아웃도 post 처리
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
    //임시 user 생성 
    @Bean
    UserDetailsService users(){
        UserDetails user = User.builder()
        .username("user1")
        .password("{bcrypt}$2a$10$CbWHvUZ8xlnOM7wPatcBXOZLff.dEJ6h46lFRQ64UGgb9v9meLB1C")
        .roles("MEMBER")
        .build();
        UserDetails admin = User.builder()
        .username("admin")
        .password("{bcrypt}$2a$10$CbWHvUZ8xlnOM7wPatcBXOZLff.dEJ6h46lFRQ64UGgb9v9meLB1C")
        .roles("MEMBER", "ADMIN")
        .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
