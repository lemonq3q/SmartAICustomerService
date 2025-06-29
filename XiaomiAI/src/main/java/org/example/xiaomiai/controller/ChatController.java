package org.example.xiaomiai.controller;

import org.example.xiaomiai.entity.Conversation;
import org.example.xiaomiai.entity.Knowledge;
import org.example.xiaomiai.entity.ModelRequest;
import org.example.xiaomiai.entity.OllamaResponse;
import org.example.xiaomiai.service.ChatService;
import org.example.xiaomiai.service.ConversationService;
import org.example.xiaomiai.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private KnowledgeService knowledgeService;

    @PostMapping
    public Flux<String> request(@RequestBody ModelRequest modelRequest) {
        Flux<OllamaResponse> sharedSource = chatService.requestChat(modelRequest)
                .publish()          // 转换为ConnectableFlux
                .autoConnect(2);

        Flux<String> frontendStream = sharedSource
                .map(OllamaResponse::getResponse);

        Mono<String> fullResponse = sharedSource
                .map(OllamaResponse::getResponse)
                .reduce("", (acc, chunk) -> acc + chunk)
                .cache();

        fullResponse.subscribe(result -> {
            Conversation responseConversation = new Conversation(
                    null, modelRequest.getSessionId(), modelRequest.getUserId(), 0,
                    result, System.currentTimeMillis()
            );
            Conversation requestConversation = new Conversation(
                    null, modelRequest.getSessionId(), modelRequest.getUserId(), 1,
                    modelRequest.getRequestContent(), System.currentTimeMillis()
            );
            conversationService.addConversation(requestConversation);
            conversationService.addConversation(responseConversation);

            String knowledgeContent = "User: " + modelRequest.getRequestContent()
                    + "\nAI: " + result;
            knowledgeService.addSessionKnowledge(
                    Knowledge.SESSION_PREFIX + modelRequest.getSessionId(),
                    knowledgeContent
            );
        });

        return frontendStream;
    }
}
