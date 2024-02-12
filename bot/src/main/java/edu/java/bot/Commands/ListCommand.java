package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import java.util.stream.Collectors;

public class ListCommand extends CommandHandler{
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/list");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {
        String tracks = bot.getBD().get(update.message().chat().username()).stream()
            .map(Object::toString)
            .collect(Collectors.joining("\n"));
        if (tracks.length() == 0) {
            bot.execute(new SendMessage(update.message().chat().id(), "У вас нет отслеживаемых ссылок\n" + tracks));
        } else {
            bot.execute(new SendMessage(update.message().chat().id(), "У вас подключены следующие ссылки:\n" + tracks));
        }
    }
}
