package edu.java.domain;

import edu.java.dto.dbDTO.Chat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatDAO {
    private final JdbcTemplate jdbcTemplate;

    public void add(Long tgChatId) {
        jdbcTemplate.update("INSERT INTO chat (tg_chat_id) VALUES (?)", tgChatId);
    }

    public void remove(Long tgChatId) {
        jdbcTemplate.update("DELETE FROM chat WHERE tg_chat_id = ?", tgChatId);
    }

    public Optional<Chat> findById(Long tgChatId) {
        List<Chat> chats = jdbcTemplate.query(
            "SELECT * FROM chat WHERE tg_chat_id = ?",
            new BeanPropertyRowMapper<>(Chat.class),
            tgChatId
        );
        if (chats.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(chats.getFirst());
    }

    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat", new BeanPropertyRowMapper<>(Chat.class));
    }
}
