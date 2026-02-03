package com.example.novels.common;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.novels.member.filter.JWTCheckFilter;
import com.example.novels.member.handler.LoginFailurHandler;
import com.example.novels.member.handler.LoginSuccessHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableMethodSecurity // @PreAuthorize, @PostAuthorize 사용 가능
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/v3/api-docs/**").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/novels/**").permitAll()
            .anyRequest().authenticated());
        http.csrf(csrf -> csrf.disable());
        http.formLogin(login -> login.loginPage("/api/member/login").permitAll()
            .successHandler(loginSuccessHandler())
            .failureHandler(new LoginFailurHandler()));
        // cors 설정
        http.cors(httpSecurityCorsConfig -> httpSecurityCorsConfig.configurationSource(configurationSource()));

        //API 서버 상태
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 필터 지정 뒤에있는 필터 이전에 jwt필터를 넣겠다.
        http.addFilterBefore(jwtCheckFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
    }

    @Bean
    JWTCheckFilter jwtCheckFilter(){
        return new JWTCheckFilter();
    }

    @Bean
    CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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

    @Bean
    PasswordEncoder passwordEncoder(){
        // 운영, 실무, 다중 알고리즘 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // 연습, 단일 알고리즘 사용
        // return new BCryptPasswordEncoder();
    }
    // 로그인이후
    @Bean
    LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }
}
