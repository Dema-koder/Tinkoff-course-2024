package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import edu.java.bot.Commands.ChainOfCommands;
import edu.java.bot.Repo.InMemoryRepository;
import edu.java.bot.Resources.Resource;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bot {
    private TelegramBot bot;
    private ChainOfCommands chainOfCommands;
    private int lastProceedUpdateId = 0;
    private InMemoryRepository inMemoryRepository;
    private static final Logger LOGGER = LogManager.getLogger();

    public Bot(@NotEmpty String token) {
        inMemoryRepository = new InMemoryRepository();
        bot = new TelegramBot(token);
        chainOfCommands = new ChainOfCommands();

        try {
            bot.execute(new SetMyCommands(new BotCommand("/track", "начать отслеживание ссылки"),
                new BotCommand("/start", "зарегистрировать пользователя"),
                new BotCommand("/help", "вывести окно с командами"),
                new BotCommand("/untrack", "прекратить отслеживание ссылки"),
                new BotCommand("/list", "показать список отслеживаемых ссылок")));
        } catch (Exception e) {
            LOGGER.error("Ошибка при добавлении меню");
        }
    }

    public Long getIdOfUpdate(Update update) {
        return update.message().chat().id();
    }

    public String getUsernameOfUpdate(Update update) {
        return update.message().chat().username();
    }

    public String getMessageOfUpdate(Update update) {
        return update.message().text();
    }

    public String getListOfLinks(String login) {
        return inMemoryRepository.getListOfLinks(login);
    }

    public boolean addUserToDB(String login) {
        return inMemoryRepository.addUser(login);
    }

    public boolean addLinkToTrack(String login, Resource resource) {
        return inMemoryRepository.addLink(login, resource);
    }

    public boolean removeLinkFromTrack(String login, Resource resource) {
        return inMemoryRepository.removeLink(login, resource);
    }

    public void evaluate() {
        while (true) {
            List<Update> updates = new ArrayList<>();
            while (updates.isEmpty()) {
                GetUpdates getUpdates = new GetUpdates().limit(1).offset(lastProceedUpdateId + 1).timeout(0);
                GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
                updates = updatesResponse.updates();
            }
            String request = updates.getLast().message().text();
            lastProceedUpdateId = updates.getLast().updateId();
            if (chainOfCommands.isBotCommand(request)) {
                chainOfCommands.startChain(updates.getLast(), this);
            } else {
                bot.execute(new SendMessage(updates.getLast().message().chat().id(), "Неизвестная команда"));
            }
        }
    }

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    public Update getLastUpdate() {
        while (true) {
            List<Update> updates = new ArrayList<>();
            while (updates.isEmpty()) {
                GetUpdates getUpdates = new GetUpdates().limit(1).offset(lastProceedUpdateId + 1).timeout(0);
                GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
                updates = updatesResponse.updates();
            }
            lastProceedUpdateId++;
            return updates.getLast();
        }
    }
}
