package com.example.ai.service;

import java.util.Map;

import org.springframework.ai.chat.prompt.PromptTemplate;

public class PromptFactory {
    private static final String TEMPLATE = """
            넌 카피라이터 계의 거물이야
            아래 내용을 참고해서 1~2줄 짜리 광고문구 5개 작성해
            - '제품명' : {name}
            - '브랜드명' : {brand}
            - '제품특징'{strength} : {strength}
            - '톤앤매너' : {tone}
            - '제품 키워드' : {keyword}
            - '브랜드 핵심 가치' : {value}
            """;
    public static String render(
    String name,
    String brand,
    String strength,
    String tone,
    String keyword,
    String value) {
    var pt = new PromptTemplate(TEMPLATE);
    return pt.render(Map.of("name",name,"brand",brand,"value",value,
    "strength",strength
    ,"tone",tone,"keyword",keyword));
}
}
