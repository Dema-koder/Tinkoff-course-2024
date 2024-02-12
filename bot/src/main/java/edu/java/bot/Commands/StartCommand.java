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
        bot.execute(new SendMessage(update.message().chat().id(), "Придумай себе логин"));
        Update lastUpdate = bot.getLastUpdate();
        bot.execute(new SendMessage(lastUpdate.message().chat().id(), "Привет " + lastUpdate.message().text()));
    }

}
