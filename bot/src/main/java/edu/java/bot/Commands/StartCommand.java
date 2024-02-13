package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;

public class StartCommand extends CommandHandler {
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/start");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {
        String username = bot.getUsernameOfUpdate(update);
        Long id = bot.getIdOfUpdate(update);
        boolean check = bot.addUserToDB(username);
        if (check) {
            bot.execute(new SendMessage(id, "Привет, "
                + username + "!"));
        } else {
            bot.execute(new SendMessage(id, "Ты уже регистрировался"));
        }
    }
}
