package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import jakarta.validation.constraints.NotEmpty;

public class Bot {
    private TelegramBot bot;

    public Bot(@NotEmpty String token) {
        bot = new TelegramBot(token);
    }

}
