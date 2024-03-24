package edu.java.configuration;

import edu.java.domain.ChatRepository;
import edu.java.domain.LinkRepository;
import edu.java.domain.jooq.JooqChatRepository;
import edu.java.domain.jooq.JooqLinkRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfig {
    private final DSLContext dslContext;

    @Autowired
    public JooqAccessConfig(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Bean
    public LinkRepository getLinkRepository() {
        return new JooqLinkRepository(dslContext);
    }

    @Bean
    public ChatRepository getChatRepository() {
        return new JooqChatRepository(dslContext);
    }
}
