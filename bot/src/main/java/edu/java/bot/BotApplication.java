package edu.java.bot;

import edu.java.bot.configuration.ApplicationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ApplicationConfig applicationConfig;

    @Autowired
    public BotApplication(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public static String getToken() {
        return applicationConfig.telegramToken();
    }

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
        String token = getToken();

        Bot bot = new Bot(token);
        bot.evaluate();
    }
}
