package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import edu.java.bot.Resources.Resource;
import java.net.MalformedURLException;

public class TrackCommand extends CommandHandler {
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/track");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {
        bot.execute(new SendMessage(update.message().chat().id(), "Отправьте ссылку для отслеживания"));
        update = bot.getLastUpdate();
        try {
            Resource resource = new Resource(update.message().text());
            bot.execute(new SendMessage(update.message().chat().id(), "Ссылка начала отслеживаться"));
        } catch (MalformedURLException e) {
            bot.execute(new SendMessage(update.message().chat().id(), "Ссылка некорректна"));
        }
    }
}
