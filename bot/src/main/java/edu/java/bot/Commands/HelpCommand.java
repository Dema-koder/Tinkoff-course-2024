package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;

public class HelpCommand extends CommandHandler {
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/help");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {
        String response = "/start -- зарегистрировать пользователя\n"
            + "/help -- вывести окно с командами\n"
            + "/track -- начать отслеживание ссылки\n"
            + "/untrack -- прекратить отслеживание ссылки\n"
            + "/list -- показать список отслеживаемых ссылок";
        bot.execute(new SendMessage(update.message().chat().id(), response));
    }
}
