package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Locker;
import com.example.jpa.entity.SportsMember;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerRepositoryTest {
    @Autowired
    private LoockerRepository loockerRepository;
    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    @Test
    public void insertTest(){
        IntStream.rangeClosed(1, 10).forEach(i->{
            Locker locker = Locker.builder().name("locker"+i).build();
            SportsMember sportsMember = SportsMember.builder().name("user"+i).locker(locker).build();

            loockerRepository.save(locker);
            sportsMemberRepository.save(sportsMember);
        });
    }
    @Transactional
    @Test
    public void readTest(){
        // 회원 조회 -> 내부 조인됨을 확인가능
        SportsMember member = sportsMemberRepository.findById(2L).get();
        System.out.println(member);
        // locker 조회 -> fetch lazy한 상태에선 불가능. 내부조인을 안했기 때문에.
        // System.out.println(member.getLocker().getName());
    }
    @Test
    public void readTest2(){
        // 전체 회원 조회 -> 내부 조인됨을 확인가능
        sportsMemberRepository.findAll().forEach(m->{
            System.out.println(m);
            System.out.println(m.getLocker());    
        });   
    }
    @Test
    public void readTest3(){
        // locker 쪽에서 회원조회 그를 위해선 locker.java에 스포츠멤버 선언 필요하다
        Locker locker = loockerRepository.findById(2L).get();
        System.out.println(locker);
        // 회원조회
        System.out.println(locker.getSportsMember().getName());
    }
    @Test
    public void readTest4(){
        // 전체 락커 조회 -> onotoone 양방향일 때 left 조인
        loockerRepository.findAll().forEach(locker -> {
            // 락커 정보
            System.out.println(locker);
            // 회원 정보
            // System.out.println(locker.getSportsMember());
        });
    }

    @Test
    public void deleteTest(){
        // 외래키 변경 or 먼저 삭제
        sportsMemberRepository.deleteById(1L);
        loockerRepository.deleteById(1L);

    }


}
