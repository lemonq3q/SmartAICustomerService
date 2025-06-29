package org.example.xiaomiai.service;

import org.example.xiaomiai.entity.ModelRequest;
import org.example.xiaomiai.entity.OllamaResponse;
import reactor.core.publisher.Flux;

public interface ChatService {
    Flux<OllamaResponse> requestChat(ModelRequest modelRequest);
}
