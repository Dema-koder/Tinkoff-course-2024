package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;

public class StartCommand extends CommandHandler{
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/start");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {
        boolean check = bot.addUserToDB(update.message().chat().username());
        if (check) {
            bot.execute(new SendMessage(update.message().chat().id(), "Привет, " + update.message().chat().username() + "!"));
        } else {
            bot.execute(new SendMessage(update.message().chat().id(), "Ты уже регистрировался"));
        }
    }
}
