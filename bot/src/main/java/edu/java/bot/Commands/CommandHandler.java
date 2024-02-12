package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.Bot;

public abstract class CommandHandler {
    private CommandHandler nextHandler;

    public void setNextHandler(CommandHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handleRequest(Update update, Bot bot) {
        if (canHandle(update.message().text())) {
            processRequest(update, bot);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(update, bot);
        }
    }

    protected abstract boolean canHandle(String request);

    protected abstract void processRequest(Update update, Bot bot);
}
