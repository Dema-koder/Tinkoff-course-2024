package edu.java.domain.jdbc;

import edu.java.dto.dbDTO.ChatToLink;
import edu.java.dto.dbDTO.Link;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LinkDAO {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public Link addLink(Long chatId, String linkName) {
        String type;
        if (linkName.startsWith("https://gitub.com")) {
            type = "GithubLink";
        } else {
            type = "StackOverFlowLink";
        }
        jdbcTemplate.update("INSERT INTO link (link_name, type) VALUES (?, ?)", linkName, type);
        Long linkId = findAllLink().getLast().getId();
        jdbcTemplate.update("INSERT INTO chat_to_link (chat_id, link_id) VALUES (?, ?)", chatId, linkId);
        return findAllLinksByTgId(chatId).getLast();
    }

    @Transactional
    public Optional<Long> getIdByLinkName(String linkName) {
        var list = jdbcTemplate.query("SELECT id FROM link WHERE link_name = ?",
            (resultSet, row) -> resultSet.getLong("id"),
            linkName
        );
        if (!list.isEmpty()) {
            return Optional.of(list.getLast());
        }
        return Optional.empty();
    }

    @Transactional
    public Link removeLink(Long chatId, String linkName) {
        Optional<Long> id = getIdByLinkName(linkName);
        List<ChatToLink> chatToLink = findAllChatLink().stream()
            .filter(entry -> Objects.equals(entry.getLinkId(), id.get()))
            .toList();
        Link link = findByChatIdAndUrl(chatId, linkName).get();
        jdbcTemplate.update("DELETE FROM chat_to_link WHERE chat_id = ? AND link_id = ?", chatId, id.get());
        if (chatToLink.size() == 1) {
            jdbcTemplate.update("DELETE FROM link WHERE id = ?", id.get());
        }
        return link;
    }

    @Transactional
    public Optional<Link> findByChatIdAndUrl(Long chatId, String linkName) {
        List<Link> links = jdbcTemplate.query("SELECT DISTINCT * FROM chat_to_link c JOIN link l "
            + "ON c.link_id = l.id WHERE c.chat_id = ?"
            + " AND l.link_name = ?", new BeanPropertyRowMapper<>(Link.class), chatId, linkName);
        if (links.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(links.getFirst());
    }

    public List<Link> findAllLink() {
        return jdbcTemplate.query("SELECT * FROM link", new BeanPropertyRowMapper<>(Link.class));
    }

    public List<ChatToLink> findAllChatLink() {
        return jdbcTemplate.query("SELECT * FROM chat_to_link", new BeanPropertyRowMapper<>(ChatToLink.class));
    }

    public void updateLastUpdate(Timestamp update, String name) {
        jdbcTemplate.update("UPDATE link SET last_update = (?) WHERE link_name = (?)", update, name);
    }

    @Transactional
    public List<Long> findAllChatsByLink(Link link) {
        return jdbcTemplate.query("SELECT DISTINCT c.chat_id FROM chat_to_link c "
                + "JOIN link l ON l.id = c.link_id WHERE l.link_name = (?)",
            (resultSet, row) -> resultSet.getLong("id"), link.getLinkName()
        );
    }

    @Transactional
    public List<Link> findAllLinksByTgId(Long tgChatId) {
        return jdbcTemplate.query("SELECT * FROM link WHERE id IN"
                + "(SELECT link_id FROM chat_to_link WHERE chat_id = (?))",
            new BeanPropertyRowMapper<>(Link.class), tgChatId
        );
    }

    public void updateLastCheck(String linkName, Timestamp lastCheck) {
        jdbcTemplate.update("UPDATE link SET last_check = (?) WHERE link_name = (?)", lastCheck, linkName);
    }

    public void updatePushedAt(String linkName, Timestamp pushedAt) {
        jdbcTemplate.update("UPDATE link SET last_commit = (?) WHERE link_name = (?)", pushedAt, linkName);
    }

    public void updateAnswerCount(String linkName, int amount) {
        jdbcTemplate.update("UPDATE link answer_count = (?) WHERE link_name = (?)", amount, linkName);
    }
}
