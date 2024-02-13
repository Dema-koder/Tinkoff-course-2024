package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;

public class ListCommand extends CommandHandler {
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/list");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {
        String username = bot.getUsernameOfUpdate(update);
        Long id = bot.getIdOfUpdate(update);
        String tracks = bot.getListOfLinks(username);
        if (tracks.isEmpty()) {
            bot.execute(new SendMessage(id, "У вас нет отслеживаемых ссылок\n" + tracks));
        } else {
            bot.execute(new SendMessage(id, "У вас подключены следующие ссылки:\n" + tracks));
        }
    }
}
