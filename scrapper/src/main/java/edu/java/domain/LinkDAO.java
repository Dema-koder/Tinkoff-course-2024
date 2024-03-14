package edu.java.domain;

import edu.java.dto.dbDTO.Chat;
import edu.java.dto.dbDTO.ChatToLink;
import edu.java.dto.dbDTO.Link;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LinkDAO {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void addLink(Long chatId, String linkName, Timestamp lastUpdate) {
        jdbcTemplate.update("INSERT INTO link (link_name, last_update) VALUES (?, ?)", linkName, lastUpdate);
        Long linkId = findAllLink().getLast().getId();
        jdbcTemplate.update("INSERT INTO chat_to_link (chat_id, link_id) VALUES (?, ?)", chatId, linkId);
    }

    @Transactional
    public Optional<Long> getIdByLinkName(String linkName) {
        var list = jdbcTemplate.query(
            "SELECT id FROM link WHERE link_name = ?",
            (resultSet, row) -> resultSet.getLong("id"),
            linkName
        );
        if (!list.isEmpty())
            return Optional.of(list.getLast());
        return Optional.empty();
    }

    @Transactional
    public void removeLink(Long chatId, String linkName) {
        Optional<Long> id = getIdByLinkName(linkName);
        List<ChatToLink> chatToLink = findAllChatLink().stream()
            .filter(entry -> Objects.equals(entry.getLinkId(), id.get()))
            .toList();
        jdbcTemplate.update("DELETE FROM chat_to_link WHERE chat_id = ? AND link_id = ?", chatId, id.get());
        if (chatToLink.size() == 1) {
            jdbcTemplate.update("DELETE FROM link WHERE id = ?", id.get());
        }
    }

    @Transactional
    public List<Link> findAllLink() {
        return jdbcTemplate.query("SELECT * FROM link", new BeanPropertyRowMapper<>(Link.class));
    }

    @Transactional
    public List<ChatToLink> findAllChatLink() {
        return jdbcTemplate.query("SELECT * FROM chat_to_link", new BeanPropertyRowMapper<>(ChatToLink.class));
    }
}
