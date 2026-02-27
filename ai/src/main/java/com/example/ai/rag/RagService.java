package com.example.ai.rag;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class RagService {
    private final InMemoryDocumentVectorStore vectorStore;
    private final DocumentProcessingService documentProcessingService;
    private final ChatClient chatClient;

    public String uploadPdfFile(File file, String oriFileName){
        var docId = UUID.randomUUID().toString();
        log.info("PDF 문서 업로드 파일 {},{}", oriFileName, docId);
        var docMetaData = new HashMap<String, Object>();
        docMetaData.put("originalFileName",oriFileName);
        docMetaData.put("uploadTime", System.currentTimeMillis());

        try {
            addDocumentFile(docId,file,docMetaData);
            log.info("PDF 문서 업로드 완료 ID : {} ", docId);
            return docId;
        } catch (Exception e) {
            log.error("문서 업로드 오류 발생");
            throw new DocumentProcessingException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    private void addDocumentFile(String docId, File file, Map<String, Object> metadata){
        log.info("파일 문서 추가 {}, {}",docId,file.getName());

        try {
            String extension =getExtensionLower(file.getName());
            String fileText = null;
            if ("pdf".equals(extension)) {
                fileText = documentProcessingService.extractTextFormPdf(file);
            }else{
                fileText = Files.readString(file.toPath());
            }
            log.debug("파일 테스트 추출 완료 - 길이 {}", file.length());
            vectorStore.addDocument(docId, fileText, metadata);
        } catch (Exception e) {
            throw new DocumentProcessingException(
                HttpStatus.INTERNAL_SERVER_ERROR, "텍스트 추출 실패"+e.getMessage(), e);
        }
    }

    public List<DocumentSearchResultDTO> retrieve(String question, int maxResults){
        log.debug("검색시작 {} 최대결과 수 {}", question, maxResults);
        // 벡터 db 검색
        return vectorStore.similaritySearch(question, maxResults);
    }

    public String generateAnswerWithContexts(String question, List<DocumentSearchResultDTO> reDocs, String conversationId){
        if(reDocs.isEmpty()){
            log.info("관련 정보를 찾을 수 없음 {}", question);
            return "관련 정보를 찾을 수 없습니다. 다른 질문을 시도하거나 관련 문서를 업로드 해 주세요.";
        }

        List<String> numberedDocs = IntStream.range(0, reDocs.size())
        .mapToObj(i -> "[" + (i + 1) + "]" + reDocs.get(i).getContent())
        .collect(Collectors.toList());

        String context = String.join("\n\n", numberedDocs);
        log.debug("컨텍스트 크기 : {}", context.length());

        String system = """
                당신은 지식 기반 Q&A 시스템이다.
                사용자의 질문에 대한 답변을 다음 정보를 바탕으로 생성해.
                주어진 정보에 답이 없다면 모른다고 대답해.
                답변 마지막에 사용한 정보의 출처 번호 [1], [2] 등을 반드시 포함해.

                정보 : 
                %s 
                """.formatted(context);

        String answer = chatClient.prompt()
        .system(system)
        .user(question)
        .advisors(a-> a.param(ChatMemory.CONVERSATION_ID,conversationId))
        .call()
        .content();
        return answer;
    }



    private String getExtensionLower(String filename){
        int idx = filename.lastIndexOf(".");
        if (idx < 0 || idx == filename.length() -1 ) {
            return "";
        }
        return filename.substring(idx+1).toLowerCase();
    }
}
