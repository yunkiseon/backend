package com.example.memo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // spring 환경 설정 파일 
public class RootConfig {
    @Bean // 객체 생성해서 스프링 컨테이너가 관리
        ModelMapper getMapper(){ // 원래 있던 public이 흐려진 이유는 Bean 을 쓰면 public을 안써도 되기 때문이다. 
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
        .setFieldMatchingEnabled(true)//필드명이 같다면 매칭을 해라
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)// getter, setter 없이도 private 필드에 접근 허용
        .setMatchingStrategy(MatchingStrategies.LOOSE);// 매칭 전략->userName 과 user_name이 같지않지만(java와 db차이) 비슷한 이름이면 알아서 매핑해라
        return modelMapper;
    }
}
