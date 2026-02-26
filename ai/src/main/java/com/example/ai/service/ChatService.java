package com.example.ai.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import com.example.ai.domain.request.AdCopyRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatService {
    
    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;

    public String exam1(String userInput, String systemMessage){
        return chatClient.prompt().user(userInput).system(systemMessage).call().content();
    }
    public ChatResponse exam2(String userInput, String systemMessage){
        return chatClient.prompt().user(userInput).system(systemMessage).call().chatResponse();
    }
    // 프롬프트
    public ChatResponse exam3(String userInput, String systemMessage, String model){
        // opeanai chat completion 모델에서 사용하는 모든 옵션 지정가능
        ChatOptions chatOptions = ChatOptions.builder().model(model).build();
        
        Prompt prompt = Prompt.builder()
        .messages(SystemMessage.builder().text(systemMessage).build(),
        UserMessage.builder().text(userInput).build())
        .chatOptions(chatOptions)
        .build();
        
        return chatClient.prompt(prompt).call().chatResponse();
    }
    // 해당 대화(conversionId 008) 동안만 참고할 정보다.
    public String exam4(String userInput, String conversionId){
        return chatClient.prompt().advisors(a -> a.param(ChatMemory.CONVERSATION_ID,
            conversionId)).user(userInput).call().content();
    }
    public String adGenerate(AdCopyRequest request){
        Prompt prompt = Prompt.builder()
        .messages(UserMessage.builder().text(PromptFactory.render(request.name(),
        request.brand(), request.strength(), request.tone(), request.keyword(), request.value())).build())
        .build();
        return chatClient.prompt(prompt).call().content();
    }

    public  EmbeddingResponse embed(String message){
        EmbeddingResponse embeddingResponse = embeddingModel.embedForResponse(List.of(message));
        return embeddingResponse;

    }
}
