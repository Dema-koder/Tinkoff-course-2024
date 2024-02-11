package edu.java.bot;

public class ChainOfCommands {
    private StartCommand startCommand;
    private HelpCommand helpCommand;

    public ChainOfCommands() {
        startCommand = new StartCommand();
        helpCommand = new HelpCommand();

        startCommand.setNextHandler(helpCommand);
    }

    public String startChain(String request) {
        return startCommand.handleRequest(request);
    }
}
