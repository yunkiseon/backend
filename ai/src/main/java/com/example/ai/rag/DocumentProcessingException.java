package com.example.ai.rag;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class DocumentProcessingException extends ResponseStatusException{

    public DocumentProcessingException(HttpStatusCode status, String reason, Throwable cause) {
        super(status,reason,cause);
    }
    
}
