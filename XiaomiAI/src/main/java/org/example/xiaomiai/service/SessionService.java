package org.example.xiaomiai.service;

import org.example.xiaomiai.entity.Session;

import java.util.List;

public interface SessionService {

    Session createSession(Session session);

    List<Session> selectSessionByUser(int userId);

    int deleteSession(Session session);
}
