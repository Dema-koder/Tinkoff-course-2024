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
            boolean check = bot.addLinkToTrack(update.message().chat().username(), resource);
            if (check) {
                bot.execute(new SendMessage(update.message().chat().id(), "Ссылка начала отслеживаться"));
            } else {
                bot.execute(new SendMessage(update.message().chat().id(), "Данная ссылка уже отслеживается"));
            }
        } catch (MalformedURLException e) {
            bot.execute(new SendMessage(update.message().chat().id(), "Ссылка некорректна"));
        }
    }
}
