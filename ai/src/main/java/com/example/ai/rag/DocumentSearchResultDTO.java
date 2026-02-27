package com.example.ai.rag;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentSearchResultDTO {
    
    @Schema(description = "문서 ID")
    private String id;

    @Schema(description = "문서 내용")
    private String content;

    @Schema(description = "문서 메타 데이터")
    private Map<String,Object> metadata;
    
    @Schema(description = "유사도 점수")
    private double score;


}
