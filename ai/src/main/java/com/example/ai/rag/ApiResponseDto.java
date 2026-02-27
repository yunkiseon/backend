package com.example.ai.rag;

// 응답 객체 - 성공과 실패시 둘다 사용
public record ApiResponseDto<T>(boolean success, T data, String errorMsg) {
    public static <T> ApiResponseDto<T> success(T data){
        return new ApiResponseDto<>(true, data, null);
    }
    public static <T> ApiResponseDto<T> failure(String errorMsg){
        return new ApiResponseDto<>(false, null, errorMsg);
    }
}
