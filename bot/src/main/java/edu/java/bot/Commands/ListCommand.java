package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.Bot;

public class ListCommand extends CommandHandler {
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/list");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {

    }
}
