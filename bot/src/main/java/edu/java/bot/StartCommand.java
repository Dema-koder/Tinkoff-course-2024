package edu.java.bot;

public class StartCommand extends CommandHandler{
    @Override
    protected boolean canHandle(String request) {
        if (request.equals("/start")) {
            return true;
        }
        return false;
    }

    @Override
    protected String processRequest(String request) {
        return "Добро пожаловать в бота!";
    }
}
