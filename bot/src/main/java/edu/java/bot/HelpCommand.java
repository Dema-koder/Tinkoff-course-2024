package edu.java.bot;

public class HelpCommand extends CommandHandler{
    @Override
    protected boolean canHandle(String request) {
        if (request.equals("/help")) {
            return true;
        }
        return false;
    }

    @Override
    protected String processRequest(String request) {
        return "Вам доступны следующие комманды: ...";
    }
}
