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
        Long idOfChat = bot.getIdOfUpdate(update);
        bot.execute(new SendMessage(idOfChat, "Отправьте ссылку для отслеживания"));
        Update lastUpdate = bot.getLastUpdate();
        String username = bot.getUsernameOfUpdate(lastUpdate);
        String message = bot.getMessageOfUpdate(lastUpdate);
        try {
            Resource resource = new Resource(message);
            boolean check = bot.addLinkToTrack(username, resource);
            if (check) {
                bot.execute(new SendMessage(idOfChat, "Ссылка начала отслеживаться"));
            } else {
                bot.execute(new SendMessage(idOfChat, "Данная ссылка уже отслеживается"));
            }
        } catch (MalformedURLException e) {
            bot.execute(new SendMessage(idOfChat, "Ссылка некорректна"));
        bot.execute(new SendMessage(update.message().chat().id(), "Отправьте ссылку для отслеживания"));
        Update lastUpdate = bot.getLastUpdate();
        try {
            Resource resource = new Resource(lastUpdate.message().text());
            bot.execute(new SendMessage(lastUpdate.message().chat().id(), "Ссылка начала отслеживаться"));
        } catch (MalformedURLException e) {
            bot.execute(new SendMessage(lastUpdate.message().chat().id(), "Ссылка некорректна"));
        }
    }
}
