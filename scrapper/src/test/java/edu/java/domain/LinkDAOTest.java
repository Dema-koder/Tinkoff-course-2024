package edu.java.domain;

import edu.java.dto.dbDTO.Chat;
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
    private final ChatDAO chatDAO = new ChatDAO(jdbcTemplate);
    private final LinkDAO linkDAO = new LinkDAO(jdbcTemplate);

    @Test
    @Rollback
    @Transactional
    public void testAdd() {
        chatDAO.add(1L);
        Long id1 = chatDAO.findById(1L).get().getId();
        linkDAO.addLink(id1, "helloworld1", new Timestamp(1L));
        linkDAO.addLink(id1, "helloworld2", new Timestamp(1L));
        List<Link> links = linkDAO.findAllLink();

        assertThat("helloworld1").isEqualTo(links.getFirst().getLinkName());
        assertThat("helloworld2").isEqualTo(links.get(1).getLinkName());

        chatDAO.remove(1L);
        chatDAO.remove(2L);
    }

}
