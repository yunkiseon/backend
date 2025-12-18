package com.example.club.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.club.entity.constant.ClubMemberRole;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "club_member")
@Entity
public class Member {
    
    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    // 구조자체는 manytoone 의 반대인 onetomany에 쓰는 것과 유사하다.
    // 차이점은 enum과 fetch 된 점
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ClubMemberRole> roles = new HashSet<>();

    public void addMemberRole(ClubMemberRole role){
        roles.add(role);
    }
}
