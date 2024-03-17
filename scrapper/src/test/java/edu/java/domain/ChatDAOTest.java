package edu.java.domain;

import edu.java.dto.dbDTO.Chat;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ChatDAOTest extends IntegrationTest {
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
    public void addAndRemoveTest() {
        chatDAO.add(1L);
        chatDAO.add(2L);
        List<Chat>chats = chatDAO.findAll();

        assertThat(1L).isEqualTo(chats.get(0).getTgChatId());
        assertThat(2L).isEqualTo(chats.get(1).getTgChatId());

        chatDAO.remove(1L);
        chatDAO.remove(2L);
        chats = chatDAO.findAll();

        assertThat(0).isEqualTo(chats.size());
    }
}
