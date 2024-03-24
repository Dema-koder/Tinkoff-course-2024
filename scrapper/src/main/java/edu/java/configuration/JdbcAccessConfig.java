package edu.java.configuration;

import edu.java.domain.jdbc.ChatDAO;
import edu.java.domain.jdbc.LinkDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAccessConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public LinkDAO getLinkRepository() {
        return new LinkDAO(jdbcTemplate);
    }

    @Bean
    public ChatDAO getChatRepository() {
        return new ChatDAO(jdbcTemplate);
    }
}
