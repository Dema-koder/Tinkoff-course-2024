package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.Bot;

public class UntrackCommand extends CommandHandler{
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/untrack");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {

    }
}
