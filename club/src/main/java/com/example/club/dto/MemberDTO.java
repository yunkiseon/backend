package com.example.club.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MemberDTO extends User implements OAuth2User{
    // member entity 정보를 담을 수 있어야 하고 + 인증정보도 같이 담아 주어야 한다. 
    // security 이기에 User을 extends 해주어야 한다.
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    // OAuth2User 가 넘겨주는 attr 담기 위해서
    private Map<String, Object> attr;

    public MemberDTO(String username, String password, boolean fromSocial,
        Collection<? extends GrantedAuthority> authorities) {
            super(username, password, authorities);
            this.fromSocial = fromSocial;
            this.email = username;
            this.password = password;
            
        }
        // oauth2
    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
        public MemberDTO(String username, String password, boolean fromSocial,
        Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
            this(username, password, fromSocial, authorities);
            this.attr = attr;
            
            
        }
    }