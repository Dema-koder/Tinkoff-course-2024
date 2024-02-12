package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import edu.java.bot.Commands.ChainOfCommands;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class Bot {
    private TelegramBot bot;
    private ChainOfCommands chainOfCommands;
    private int lastProceedUpdateId = 0;

    public Bot(@NotEmpty String token) {
        bot = new TelegramBot(token);
        chainOfCommands = new ChainOfCommands();
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
            lastProceedUpdateId = updates.getLast().updateId();
            if (chainOfCommands.isBotCommand(request)) {
                chainOfCommands.startChain(updates.getLast(), this);
            } else {
                bot.execute(new SendMessage(updates.getLast().message().chat().id(), "Неизвестная команда"));
            }
        }
//        bot.setUpdatesListener(updates -> {
//            updates.forEach(update -> {
//                String request = update.message().text();
//                lastProceedUpdateId = update.updateId();
//                if (chainOfCommands.isBotCommand(request)) {
//                    chainOfCommands.startChain(update, this);
//                } else {
//                    bot.execute(new SendMessage(update.message().chat().id(), "Неизвестная команда"));
//                }
//            });
//            return UpdatesListener.CONFIRMED_UPDATES_ALL;
//        }, e -> {
//            if (e.response() != null) {
//                e.response().errorCode();
//                e.response().description();
//            } else {
//                e.printStackTrace();
//            }
//        });
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
