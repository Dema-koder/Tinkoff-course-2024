package edu.java.services.jdbc;

import edu.java.domain.jdbc.ChatDAO;
import edu.java.exceptions.ChatDoesNotExistException;
import edu.java.services.TgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final ChatDAO chatDAO;

    @Override
    public void register(Long tgChatId) {
        chatDAO.add(tgChatId);
    }

    @Override
    public void unregister(Long tgChatId) throws ChatDoesNotExistException {
        if (chatDAO.findById(tgChatId).isEmpty()) {
            throw new ChatDoesNotExistException("Чата не существует");
        }
        chatDAO.remove(tgChatId);
    }
}
