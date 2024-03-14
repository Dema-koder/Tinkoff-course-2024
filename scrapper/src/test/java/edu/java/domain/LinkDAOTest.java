package edu.java.domain;

import edu.java.dto.dbDTO.ChatToLink;
import edu.java.dto.dbDTO.Link;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LinkDAOTest extends IntegrationTest {
    private static final JdbcTemplate jdbcTemplate= new JdbcTemplate(DataSourceBuilder
        .create()
        .url(POSTGRES.getJdbcUrl())
        .username(POSTGRES.getUsername())
        .password(POSTGRES.getPassword())
        .build());
    private final LinkDAO linkDAO = new LinkDAO(jdbcTemplate);
    private final ChatDAO chatDAO = new ChatDAO(jdbcTemplate);

    @Test
    @Rollback
    @Transactional
    public void allFunctionalityTest() {
        chatDAO.add(1L);
        linkDAO.addLink(1L,"link", new Timestamp(1L));
        Optional<Long> linkId = linkDAO.getIdByLinkName("link");

        assertThat(linkId.get()).isEqualTo(1L);

        linkDAO.removeLink(1L, "link");

        assertThat(linkDAO.getIdByLinkName("link")).isEmpty();
    }
}
