package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.validation.constraints.NotEmpty;

public class Bot {
    private TelegramBot bot;
    ChainOfCommands chainOfCommands;

    public Bot(@NotEmpty String token) {
        bot = new TelegramBot(token);
        chainOfCommands = new ChainOfCommands();
    }

    public void update() {
        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                String response = chainOfCommands.startChain(update.message().text());
                bot.execute(new SendMessage(update.message().chat().id(), response));
            });
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
