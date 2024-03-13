package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import edu.java.bot.Resources.Resource;
import java.net.MalformedURLException;

public class UntrackCommand extends CommandHandler {
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/untrack");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {
        Long id = bot.getIdOfUpdate(update);
        bot.execute(new SendMessage(id, "Отправьте ссылку для удаления"));
        Update lastUpdate = bot.getLastUpdate();
        String username = bot.getUsernameOfUpdate(lastUpdate);
        String text = bot.getMessageOfUpdate(lastUpdate);
        try {
            Resource resource = new Resource(text);
            boolean check = bot.removeLinkFromTrack(username, resource);
            if (check) {
                bot.execute(new SendMessage(id, "Ссылка удалилась"));
            } else {
                bot.execute(new SendMessage(id, "Ссылка до этого не отслеживалась"));
            }
        } catch (MalformedURLException e) {
            bot.execute(new SendMessage(id, "Ссылка некорректна"));
        bot.execute(new SendMessage(update.message().chat().id(), "Отправьте ссылку для удаления"));
        Update lastUpdate = bot.getLastUpdate();
        try {
            Resource resource = new Resource(lastUpdate.message().text());
            bot.execute(new SendMessage(lastUpdate.message().chat().id(), "Ссылка удалилась"));
        } catch (MalformedURLException e) {
            bot.execute(new SendMessage(lastUpdate.message().chat().id(), "Ссылка некорректна"));
        }
    }
}
