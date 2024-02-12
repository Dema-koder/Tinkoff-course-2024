package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.Bot;

public class HelpCommand extends CommandHandler{
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/help");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {

    }
}
