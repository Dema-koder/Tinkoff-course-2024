package edu.java.bot.Commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.Bot;
import java.util.ArrayList;
import java.util.List;

public class ChainOfCommands {
    private List<String>allCommands;
    private StartCommand startCommand;
    private HelpCommand helpCommand;
    private TrackCommand trackCommand;
    private UntrackCommand untrackCommand;
    private ListCommand listCommand;

    public ChainOfCommands() {
        allCommands = new ArrayList<>(List.of("/start", "/help", "/track", "/untrack", "/list"));
        startCommand = new StartCommand();
        helpCommand = new HelpCommand();
        trackCommand = new TrackCommand();
        untrackCommand = new UntrackCommand();
        listCommand = new ListCommand();

        startCommand.setNextHandler(helpCommand);
        helpCommand.setNextHandler(trackCommand);
        trackCommand.setNextHandler(untrackCommand);
        untrackCommand.setNextHandler(listCommand);
    }

    public boolean isBotCommand(String request) {
        return allCommands.stream()
            .anyMatch(command -> command.equals(request));
    }

    public void startChain(Update update, Bot bot) {
        startCommand.handleRequest(update, bot);
    }
}
