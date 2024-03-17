package edu.java.domain;

import edu.java.domain.jdbc.ChatDAO;
import edu.java.domain.jdbc.LinkDAO;
import edu.java.dto.dbDTO.Link;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
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
    public void testFindAll() {
        chatDAO.add(1L);
        chatDAO.add(2L);
        linkDAO.addLink(1L, "helloworld1", new Timestamp(1L));
        linkDAO.addLink(1L, "helloworld2", new Timestamp(1L));
        linkDAO.addLink(2L, "helloworld3", new Timestamp(1L));

        List<Link> links = linkDAO.findAllLink();

        assertThat(3).isEqualTo(links.size());
        assertThat("helloworld1").isEqualTo(links.getFirst().getLinkName());
        assertThat("helloworld2").isEqualTo(links.get(1).getLinkName());
        assertThat("helloworld3").isEqualTo(links.getLast().getLinkName());

        chatDAO.remove(1L);
        chatDAO.remove(2L);
    }
}
