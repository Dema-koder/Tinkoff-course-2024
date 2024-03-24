package edu.java.configuration;

import edu.java.domain.ChatRepository;
import edu.java.domain.LinkRepository;
import edu.java.domain.jpa.DefaultJpaChatRepository;
import edu.java.domain.jpa.DefaultJpaLinkRepository;
import edu.java.domain.jpa.JpaChatRepository;
import edu.java.domain.jpa.JpaLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {
    private final JpaChatRepository jpaChatRepository;

    private final JpaLinkRepository jpaLinkRepository;

    @Autowired
    public JpaAccessConfig(JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository) {
        this.jpaChatRepository = jpaChatRepository;
        this.jpaLinkRepository = jpaLinkRepository;
    }

    @Bean
    public LinkRepository getLinkRepository() {
        return new DefaultJpaLinkRepository(jpaLinkRepository);
    }

    @Bean
    public ChatRepository getChatRepository() {
        return new DefaultJpaChatRepository(jpaChatRepository);
    }
}
