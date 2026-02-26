package com.example.ai.rag;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
@RequiredArgsConstructor
public class InMemoryDocumentVectorStore {
    private final VectorStore vectorStore;

    public void addDocument(String docId, String fileText, Map<String, Object> metadata){
        log.info("문서 추가 {},{}",docId,fileText.length());

        try {
            Map<String, Object> merged = new HashMap<>();
            if (metadata != null) {
                merged.putAll(metadata);
            }
            var document = new Document(fileText,merged);
            var textSplitter = TokenTextSplitter.builder()
            .withChunkSize(512)
            .withMinChunkSizeChars(350)
            .withMinChunkLengthToEmbed(5)
            .withMaxNumChunks(10000)
            .withKeepSeparator(true)
            .build();
            var chunks = textSplitter.split(document);
            // 내부적으로 임베딩 변환 수행하면서 벡터 스토어에 추가
            vectorStore.add(chunks);
        } catch (Exception e) {
            throw new DocumentProcessingException(HttpStatus.INTERNAL_SERVER_ERROR, "임베딩 및 저장 실패"+e.getMessage(), e);
        }
    }
}
