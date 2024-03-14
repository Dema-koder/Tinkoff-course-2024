package edu.java.domain;

import edu.java.dto.dbDTO.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatDAO {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(Long tgChatId) {
        jdbcTemplate.update("INSERT INTO chat (tg_chat_id) VALUES (?)", tgChatId);
    }

    @Transactional
    public void remove(Long tgChatId) {
        jdbcTemplate.update("DELETE FROM chat WHERE tg_chat_id = ?", tgChatId);
        jdbcTemplate.update("DELETE FROM chat_to_link WHERE chat_id = ?", tgChatId);
    }

    @Transactional
    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat", new BeanPropertyRowMapper<>(Chat.class));
    }
}
