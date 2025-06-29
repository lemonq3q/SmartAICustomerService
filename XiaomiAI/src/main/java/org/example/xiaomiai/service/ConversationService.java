package org.example.xiaomiai.service;

import org.example.xiaomiai.entity.Conversation;

import java.util.List;

public interface ConversationService {
    List<Conversation> selectConversationBySessionId(int sessionId);

    int addConversation(Conversation conversation);

    int deleteConversationBySessionId(int sessionId);
}
