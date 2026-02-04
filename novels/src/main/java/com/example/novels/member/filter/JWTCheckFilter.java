package com.example.novels.member.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Member;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.novels.member.dto.MemberDTO;
import com.example.novels.member.utils.JWTUtil;
import com.google.gson.Gson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter{

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws SecurityException{
        // api/novels 예외적용 필요
        if (request.getMethod().equals("OPTION")) {
            return true;            
        }
        String path = request.getRequestURI();
        log.info("check uri {}", path);

        if (path.startsWith("/v3/api-docs") || path.startsWith("/api/member/login")) {
            return true;
        }
        // \\d+$ 정규식 패턴 => \d == 숫자 0-9 / + : 1~무제한 / $ 끝나는 위치
        // if (path.startsWith("/api/novels") || path.matches("/api/novels/\\d+$")) {
        //     return true;
        // }
        if ("GET".equals(request.getMethod()) && path.startsWith("/api/novels")) {
            return true;
        }
        return false;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("------------JWT Filter------------");
        
        // 클라이언트는 헤더를 통해서 토큰 서버로 전송
        String authHeaerStr = request.getHeader("Authorization");
        // 토큰 시작 문자열 : Bearer
        if (authHeaerStr != null && authHeaerStr.startsWith("Bearer")) {

            try {
                // "Bearer" 이후의 문자를 분리
                String accessToken = authHeaerStr.substring(7);
                // 토큰 유효성 검증
                Map<String, Object> claims = JWTUtil.validateToken(accessToken);
                log.info("claims {}", claims);
    
                // claims 값 추출
                String email = (String)claims.get("email");
                String pw = (String)claims.get("pw");
                String nickname = (String)claims.get("nickname");
                Boolean social = (Boolean)claims.get("social");
                List<String> roles = (List<String>)claims.get("roles");
    
    
                MemberDTO memberDTO = new MemberDTO(email, pw, nickname, social, roles);
    
                // 인증정보 => SecurityContext 저장
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberDTO,pw,memberDTO.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
                
            } catch (Exception e) {
                log.error("JWT token error  ");
                log.error(e.getMessage());
                // 에러 message 전달 jsom
                Gson gson = new Gson();
                String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));
                response.setContentType("application/json");
                PrintWriter printWriter = response.getWriter();
                printWriter.println(msg);
                printWriter.close();
            }

        }
    }
    
}
