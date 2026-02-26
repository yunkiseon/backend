package com.example.ai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ai.domain.request.AdCopyRequest;
import com.example.ai.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Tag(name = "OpenAI LLM", description = "OpenAI LLM 테스트")
public class ChatController {
    private final ChatService chatService;

    @Operation(summary = "Chat Completion 테스트", description = "Spring AI 를 활용한 Chat Completion 기능 구현")
    @GetMapping("/chat1")
    public String getChat(String userInput, String systemMessage) {
        // user, system
        String result = chatService.exam1(userInput,systemMessage);
        return result;
    }
    
    @Operation(summary = "Chat Completion 테스트", description = "Spring AI 를 활용한 Chat Completion 기능 구현 : ChatResponse")
    @GetMapping("/chat2")
    public ChatResponse getChat2(String userInput, String systemMessage) {
        // user, system
        ChatResponse result = chatService.exam2(userInput,systemMessage);
        return result;
    }

    @Operation(summary = "Chat Completion 테스트", description = "Spring AI 를 활용한 Chat Completion 기능 구현 : Prompt")
    @GetMapping("/chat3")
    public ChatResponse getChat3(String userInput, String systemMessage, 
        @RequestParam(defaultValue = "gpt-4.1-nano") String model) {
        // user, system
        ChatResponse result = chatService.exam3(userInput,systemMessage,model);
        return result;
    }
    @Operation(summary = "Chat Completion 테스트", description = "Spring AI 를 활용한 Chat Completion 기능 구현 : Prompt")
    @GetMapping("/chat4")
    public String getChat4(String userInput, HttpSession session) {
        String conversionId = session.getId();
        String result = chatService.exam4(userInput, conversionId);
        return result;
    }
    
    @Operation(summary = "Chat Completion 테스트", description = "Spring AI 를 활용한 광고문구 생성")
    @PostMapping("/ad")
    public String postCopy(@RequestBody AdCopyRequest request) {
        String result = chatService.adGenerate(request);
        return result;

    }
    @Operation(summary = "Chat Completion 테스트", description = "Spring AI 를 활용한 Chat Completion 기능 구현 : embedding")
    @GetMapping("/embedded")
    public Map<String, List<Embedding>> getChat5(String userInput, HttpSession session) {
        EmbeddingResponse embeddingResponse = chatService.embed(userInput);
        return Map.of("embedding",embeddingResponse.getResults());
        }
}
