package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
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

        TelegramBot bot = new TelegramBot(token);

        bot.setUpdatesListener(updates -> {
            updates.forEach(System.out::println);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            } else {
                e.printStackTrace();
            }
        });
    }
}
