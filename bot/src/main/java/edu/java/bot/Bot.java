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
import edu.java.bot.Resources.Resource;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bot {
    private TelegramBot bot;
    private ChainOfCommands chainOfCommands;
    private int lastProceedUpdateId = 0;
    private Map<String, Set<Resource>> dataBase;
    private static final Logger LOGGER = LogManager.getLogger();

    public void setDataBase(Map<String, Set<Resource>> dataBase) {
        this.dataBase = dataBase;
    }

    public Bot(@NotEmpty String token) {
        bot = new TelegramBot(token);
        chainOfCommands = new ChainOfCommands();
        dataBase = new HashMap<>();

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
        String links = dataBase.get(login).stream()
            .map(Object::toString)
            .collect(Collectors.joining("\n"));
        return links;
    }

    public boolean addUserToDB(String login) {
        if (dataBase.containsKey(login)) {
            return false;
        }
        dataBase.put(login, new HashSet<>());
        return true;
    }

    public boolean addLinkToTrack(String login, Resource resource) {
        if (dataBase.get(login).contains(resource)) {
            return false;
        }
        dataBase.get(login).add(resource);
        return true;
    }

    public boolean removeLinkFromTrack(String login, Resource resource) {
        boolean check = false;
        Resource cur = new Resource();
        for (var res: dataBase.get(login)) {
            if (res.toString().equals(resource.toString())) {
                check = true;
                cur = res;
            }
        }
        if (check) {
            dataBase.get(login).remove(cur);
            return true;
        }
        return false;
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
