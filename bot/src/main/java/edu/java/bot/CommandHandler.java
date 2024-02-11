package edu.java.bot;

public abstract class CommandHandler {
    private CommandHandler nextHandler;

    public void setNextHandler(CommandHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public String handleRequest(String request) {
        if (canHandle(request)) {
            return processRequest(request);
        } else if (nextHandler != null) {
            return nextHandler.handleRequest(request);
        } else {
            return "Не могу выполнить данную команду";
        }
    }

    protected abstract boolean canHandle(String request);

    protected abstract String processRequest(String request);
}
