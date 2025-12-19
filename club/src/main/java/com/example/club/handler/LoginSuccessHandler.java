package com.example.club.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.club.dto.MemberDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                // 로그인 성공 후 경로 지정 
                // role_user -> /member/profile
                // role_manager -> /manager/info
                // role_admin -> /admin/manage
                // authentication.getName() -> id 가져오기
                // role 정보는 authentication에 들어있음 경로가 principal->authoritues->authority or authorities":[{"authority":"ROLE_USER"}
                // authentication.getAuthorities()
                MemberDTO dto = (MemberDTO)authentication.getPrincipal();// 이 방법이 더 자주 쓰인다.
                List<String> roleNames = new ArrayList<>();
                dto.getAuthorities().forEach(auth -> {
                    roleNames.add(auth.getAuthority());
                });
                
                if (roleNames.contains("ROLE_ADMIN")) {
                response.sendRedirect("/admin/manage");
                return;
                }else if (roleNames.contains("ROLE_MANAGER")) {
                    response.sendRedirect("/manager/info");
                    return;
                }else if (roleNames.contains("ROLE_USER")) {
                response.sendRedirect("/member/profile");// 스프링에서 redirect:/member/profile과 동일한 역할
                    return;
                }
                response.sendRedirect("/");
    }
    
}
