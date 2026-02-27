package com.example.ai.rag;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RequiredArgsConstructor
@Log4j2
@RestController
@Tag(name = "OpenAI LLM", description = "OpenAI LLM 테스트 - RAG")
public class RagController {
    private final RagService ragService;
    private final ChatClient chatClient;
     
    // multipartfile를 쓴 다는 것은 화면단에서 enctype으로 온다->mulipart/form-data
    // consumes 는 정확하게 한 번 더 알려주는 것
    @PostMapping(value = "/document",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto> handleFileUpload(MultipartFile file){
        log.info("파일 업로드 {}",file.getOriginalFilename());
        if (file.isEmpty()) {
            log.warn("빈 파일 ");
            return ResponseEntity.badRequest().body(ApiResponseDto.failure("파일이 비어 있습니다."));
        }
        if (!file.getOriginalFilename().endsWith(".pdf") && !file.getOriginalFilename().endsWith(".PDF")) {
            log.warn("지원하지 않는 파일 형식");
            return ResponseEntity.badRequest().body(ApiResponseDto.failure("PDF 파일만 업로드 가능합니다"));
        }

        // Service 호출

        File tempFile = null;
        
        try {
            // 파일저장
            tempFile = File.createTempFile("upkoad_", ".pdf");
            log.debug("임시 파일 저장 {}", tempFile.getAbsolutePath());
            file.transferTo(tempFile);
        } catch (Exception e) {
            log.warn("임시 파일 저장 중 에러");
            return ResponseEntity.badRequest().body(ApiResponseDto.failure("파일 저장 중 오류 발생"));
    
        }
        try {
            String docId = ragService.uploadPdfFile(tempFile, file.getOriginalFilename());
            log.info("문서 업로드 성공 {}",docId);
            return ResponseEntity.ok(ApiResponseDto.success(docId));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            // 임시 파일 제거
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponseDto.failure("알 수 없는 오류 발생"));
    }

    @PostMapping("/api/v1/rag")
    public ResponseEntity<ApiResponseDto<Map<String,String>>> postRag(@RequestBody QueryRequestDto requestDto,
        @RequestHeader(value = "X-CHAT-ID", required = false) String chatID) {
        log.info("질의 요청 {}", requestDto);
        if (requestDto.getQuery().isBlank()) {
            log.info("빈 질의 요청");
            return ResponseEntity.badRequest().body(ApiResponseDto.failure("질의가 비어 있습니다"));
        }
        String conversationId = (chatID == null || chatID.isBlank()) ? UUID.randomUUID().toString():chatID;
        List<DocumentSearchResultDTO> resultDto = ragService.retrieve(requestDto.getQuery(), requestDto.getMaxResults());
        String answer = ragService.generateAnswerWithContexts(requestDto.getQuery(),resultDto,conversationId);
        return ResponseEntity.ok(ApiResponseDto.success(Map.of("conversionId",conversationId,"answer",answer)));
    }
    
}
