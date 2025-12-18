package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncoder(){
        String password = "1111";
        // 입력 비밀번호 -> 암호화
        String encodePass = passwordEncoder.encode(password);
        //{bcrypt}$2a$10$CbWHvUZ8xlnOM7wPatcBXOZLff.dEJ6h46lFRQ64UGgb9v9meLB1C
        // 이 방식은 단방향 암호화다. 1111 로 이 코드를 도출할 수 있지만 반대는 안된다.
        // config에서 retrun 값을 연습으로 수정하니까 $2a$10$QyqaIje.BSQNPDivw4/sYOuql5.BJVg4OKsQ5K5uaikrbHvdLGILi
        // 로 나온다. 값은 당연히 바뀌지만 차이로 {} 가 없는 것, 단일 알고리즘을 사용하는 차이이다.
        System.out.println("raw password " + password + " encode password " + encodePass);
        System.out.println(passwordEncoder.matches(password, encodePass));
        System.out.println(passwordEncoder.matches("2222", encodePass));
    }
}
