package org.example.xiaomiai.service.impl;

import org.example.xiaomiai.entity.Knowledge;
import org.example.xiaomiai.entity.ModelRequest;
import org.example.xiaomiai.entity.OllamaRequestBody;
import org.example.xiaomiai.entity.OllamaResponse;
import org.example.xiaomiai.service.ChatService;
import org.example.xiaomiai.service.KnowledgeService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private WebClient webClient;

    @Override
    public Flux<OllamaResponse> requestChat(ModelRequest modelRequest) {
        String ragData = "";
        ragData += "你是一个智能问答助手，请根据知识库的数据以及你何用户的历史对话回答问题\n";
        if(modelRequest.getStoreName()!= null && modelRequest.getStoreName()!=""){
            ragData += "以下为知识库中的知识: \n";
            ragData += knowledgeService.getRagData(modelRequest.getRequestContent(), modelRequest.getStoreName());
        }
        if(modelRequest.getSessionId()!=null){
            ragData += "以下为你何用户的历史聊天记录: \n";
            ragData += knowledgeService.getRagData(modelRequest.getRequestContent(), Knowledge.SESSION_PREFIX + Integer.toString(modelRequest.getSessionId()));
        }

        String userPrompt = ragData + "\n以下是用户的提问: \n" + modelRequest.getRequestContent();
        System.out.println(userPrompt);
        OllamaRequestBody requestBody = new OllamaRequestBody(modelRequest.getModelName(), userPrompt, true);
        return webClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(OllamaResponse.class);
    }

}
