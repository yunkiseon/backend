package com.example.novels.member.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.novels.member.entity.constant.MemberRole;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class MemberDTO extends User{
    // member entity 정보를 담을 수 있어야 하고 + 인증정보도 같이 담아 주어야 한다. 
    // security 이기에 User을 extends 해주어야 한다.
   // 로그인 이후 인정정보
    private String email;

    
    private String pw;

    
    private String nickname;

    private List<String> roles = new ArrayList<>();
    private boolean fromSocial;

    // OAuth2User 가 넘겨주는 attr 담기 위해서
    private Map<String, Object> attr;

    public MemberDTO(String username, String pw,String nickname, boolean fromSocial,
        List<String> roles) {
            super(username, pw, roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toList()));
            this.fromSocial = fromSocial;
            this.email = username;
            this.pw = pw;
            this.roles = roles;
            this.nickname = nickname;
        }

        public Map<String, Object> getClaims(){
            Map<String,Object> dataMap = new HashMap<>();

            dataMap.put("email", email);
            dataMap.put("pw", pw);
            dataMap.put("nickname", nickname);
            dataMap.put("social", fromSocial);
            dataMap.put("roles", roles);
            return dataMap;
        }

    
        // oauth2
    // @Override
    // public Map<String, Object> getAttributes() {
    //     return this.attr;
    // }
    //     public MemberDTO(String username, String pw, boolean fromSocial,String nickname,
    //     Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
    //         this(username, pw,nickname, fromSocial, authorities);
    //         this.attr = attr;
            
            
    //     }
    }