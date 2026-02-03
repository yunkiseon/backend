package com.example.novels.member.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.novels.member.dto.MemberDTO;
import com.example.novels.member.utils.JWTUtil;
import com.google.gson.Gson;

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

                log.info("-------------------");
                log.info("인증받은 객체 {}",dto);
                log.info("-------------------");

                Map<String, Object> claims = dto.getClaims();

                // 토큰 생성
                String accessToken = JWTUtil.generateToken(claims, 10);
                String refreshToken = JWTUtil.generateToken(claims, 10 * 60);

                claims.put("accessToken", accessToken);
                claims.put("refreshToken", refreshToken);

                // 클라이언트에게 전송
                Gson gson = new Gson();
                String msg = gson.toJson(claims);
                response.setContentType("application/json;촘ㄱㄴㄷㅅ=ㅕㅅㄹ-8");
                PrintWriter printWriter = response.getWriter();
                printWriter.println(msg);
                printWriter.close();
               
    }
    
}
