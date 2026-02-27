package com.example.ai.rag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequestDto{
    @Schema(description = "사용자 질문",example = "인공지능이란 무엇인가요?")
    private String query;
    @Schema(description = "최대 검색 결과 수",example ="3",defaultValue="3")
    private Integer maxResults;
    @Schema(description = "LLM 모델",example ="gpt-3.5-turbo",defaultValue="gpt-4o-mini")
    private String model = "gpt-4o-mini";
}
