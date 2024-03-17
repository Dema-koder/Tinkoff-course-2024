package edu.java.domain;

import edu.java.dto.dbDTO.Chat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ChatDAO {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(Long tgChatId) {
        if (findById(tgChatId).isEmpty()) {
            jdbcTemplate.update("INSERT INTO chat (tg_chat_id) VALUES (?)", tgChatId);
        }
    }

    @Transactional
    public void remove(Long tgChatId) {
        jdbcTemplate.update("DELETE FROM chat_to_link WHERE chat_id = ?", tgChatId);
        jdbcTemplate.update("DELETE FROM chat WHERE tg_chat_id = ?", tgChatId);
    }

    @Transactional
    public Optional<Chat> findById(Long tgChatId) {
        List<Chat> chats = jdbcTemplate.query(
            "SELECT * FROM chat WHERE id = ?",
            new BeanPropertyRowMapper<>(Chat.class),
            tgChatId
        );
        if (chats.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(chats.getFirst());
    }

    @Transactional
    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat", new BeanPropertyRowMapper<>(Chat.class));
    }
}
