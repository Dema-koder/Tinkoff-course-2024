package edu.java.domain;

import edu.java.dto.dbDTO.Chat;
import edu.java.domain.jdbc.ChatDAO;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JdbcChatDAOTest extends IntegrationTest {
    private static final JdbcTemplate jdbcTemplate= new JdbcTemplate(DataSourceBuilder
        .create()
        .url(POSTGRES.getJdbcUrl())
        .username(POSTGRES.getUsername())
        .password(POSTGRES.getPassword())
        .build());
    private final ChatDAO chatDAO = new ChatDAO(jdbcTemplate);

    @Test
    @Rollback
    @Transactional
    @DisplayName("Test add method")
    public void testAdd() {
        chatDAO.add(1L);
        chatDAO.add(2L);
        List<Chat> chats = chatDAO.findAll();

        assertThat(1L).isEqualTo(chats.getFirst().getId());
        assertThat(2L).isEqualTo(chats.getLast().getId());
        chatDAO.remove(1L);
        chatDAO.remove(2L);
    }

    @Test
    @Rollback
    @Transactional
    @DisplayName("Test remove method")
    public void testRemove() {
        chatDAO.add(1L);
        List<Chat> chats = chatDAO.findAll();

        assertThat(1L).isEqualTo(chats.getFirst().getId());
        chatDAO.remove(1L);

        List<Chat> newChats = chatDAO.findAll();

        assertThat(newChats.isEmpty()).isTrue();
    }

    @Test
    @Rollback
    @Transactional
    @DisplayName("Test findAll method")
    public void testFindAll() {
        chatDAO.add(1L);
        chatDAO.add(2L);
        List<Chat> chats = chatDAO.findAll();

        assertThat(2).isEqualTo(chats.size());
        assertThat(1L).isEqualTo(chats.getFirst().getId());
        assertThat(2L).isEqualTo(chats.getLast().getId());
        chatDAO.remove(1L);
        chatDAO.remove(2L);
    }

    @Test
    @Rollback
    @Transactional
    @DisplayName("Test findById method")
    public void testFindById() {
        chatDAO.add(1L);
        Optional<Chat> chat = chatDAO.findById(1L);

        assertThat(chat.isPresent()).isTrue();
        assertThat(1L).isEqualTo(chat.get().getId());

        Optional<Chat> unknownChat = chatDAO.findById(2L);
        assertThat(unknownChat.isEmpty()).isTrue();
        chatDAO.remove(1L);
    }
}
