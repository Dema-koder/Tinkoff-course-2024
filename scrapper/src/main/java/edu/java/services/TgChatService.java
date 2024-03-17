package edu.java.services;

import edu.java.exceptions.ChatDoesNotExistException;

public interface TgChatService {
    void register(Long tgChatId);

    void unregister(Long tgChatId) throws ChatDoesNotExistException;
}
