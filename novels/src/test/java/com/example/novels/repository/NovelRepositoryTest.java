package com.example.novels.repository;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.example.novels.member.entity.Member;
import com.example.novels.member.entity.constant.MemberRole;
import com.example.novels.member.repository.MemberRepository;
import com.example.novels.novel.entity.Grade;
import com.example.novels.novel.entity.Novel;
import com.example.novels.novel.repository.GenreRepository;
import com.example.novels.novel.repository.GradeRepository;
import com.example.novels.novel.repository.NovelRepository;

// @Disabled
@SpringBootTest
@TestPropertySource(properties = {
    "springdoc.api-docs.enabled=false",
    "springdoc.swagger-ui.enabled=false"
})
public class NovelRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private NovelRepository novelRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void memberRead(){
        Optional<Member> member = memberRepository.findByEmailAndFromSocial("user11@gmail.com", false);
        System.out.println(member.get());
    }

    @Test
    public void memberInsertTest(){
        IntStream.rangeClosed(11, 20).forEach(i -> {
            Member member = Member.builder()
            .email("user"+i+"@gmail.com")
            .pw(passwordEncoder.encode("1234"))
            .nickname("user"+i).build();
            member.addRole(MemberRole.MEMBER);
            if (i > 18) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });

       // System.out.println(memberRepository.findById("user1@gmail.com"));

    }

    @Test
    public void gradeInsertTest() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            long no = (long)(Math.random()*18) +56; // 1, 25 , 56
            Novel novel = Novel.builder().id(no).build();

            int rating = (int)(Math.random()*5)+1; // 1, 25 , 56
            int j = (int)(Math.random()*10)+1;
            Member member = Member.builder().email("user"+j+"@gmail.com").build();

            Grade grade = Grade.builder().novel(novel).rating(rating).member(member).build();

            gradeRepository.save(grade);
        });
    }

    @Test
    public void readTest (){
        Object[] row = novelRepository.getNovelId(1L);
        System.out.println(row[0]);
        System.out.println(row[1]);
        System.out.println(row[2]);
    }
    @Test
    public void readTest2 (){

        Pageable pageable = PageRequest.of(1, 10, Sort.by("id").descending());
        Page<Object[]> result = novelRepository.list(null, null, pageable);

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }
}
