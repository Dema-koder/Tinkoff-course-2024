package edu.java.services.jdbc;

import edu.java.domain.jdbc.ChatDAO;
import edu.java.exceptions.ChatDoesNotExistException;
import edu.java.services.TgChatService;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JdbcTgChatServiceTest {
    @Test
    public void registerTest() {
        ChatDAO chatDAO = mock(ChatDAO.class);
        TgChatService tgChatService = new JdbcTgChatService(chatDAO);
        tgChatService.register(1L);
        verify(chatDAO).add(1L);
    }

    @Test
    public void unregisterTest() {
        ChatDAO chatDAO = mock(ChatDAO.class);
        TgChatService tgChatService = new JdbcTgChatService(chatDAO);
        when(chatDAO.findById(1L)).thenReturn(Optional.empty());
        try {
            tgChatService.unregister(1L);
        } catch (ChatDoesNotExistException e) {
            assertThat(e.getMessage()).isEqualTo("Чата не существует");
        }
    }
}
