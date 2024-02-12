package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.Bot;

public class TrackCommand extends CommandHandler {
    @Override
    protected boolean canHandle(String request) {
        return request.equals("/track");
    }

    @Override
    protected void processRequest(Update update, Bot bot) {

    }
}
