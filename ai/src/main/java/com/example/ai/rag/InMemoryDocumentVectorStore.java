package com.example.ai.rag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
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
            // 데이터 분할
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
    public List<DocumentSearchResultDTO> similaritySearch(String question, int maxResult){
        var regRequest = SearchRequest.builder()
        .query(question)
        .topK(maxResult)
        .similarityThreshold(0.3)
        .build();

        List<Document> results = vectorStore.similaritySearch(regRequest);
        if (results == null) {
            return Collections.emptyList();
        }
        return results.stream()
        .map(r->{
            Map<String, Object> md = r.getMetadata();
            String id = String.valueOf(md.getOrDefault("id", md.getOrDefault("docId", "unknown")));
            String content = r.getText() == null ? "" : r.getText();
            double score = r.getScore() == null ? 0.0 : r.getScore();

            Map<String,Object> filterded = md.entrySet().stream()
            .filter(e -> !e.getKey().equals("id") && !e.getKey().equals("docId"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            return DocumentSearchResultDTO.builder()
            .id(id)
            .content(content)
            .score(score)
            .metadata(filterded)
            .build();
        })
        .collect(Collectors.toList());
    }
}
