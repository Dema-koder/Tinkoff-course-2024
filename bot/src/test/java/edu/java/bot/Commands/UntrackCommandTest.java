package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import edu.java.bot.Resources.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UntrackCommandTest {
    private Update update;
    private Bot bot;

    @BeforeEach
    void setUp() {
        update = mock(Update.class);
        bot = mock(Bot.class);

        when(bot.getUsernameOfUpdate(any(Update.class))).thenReturn("user");
        when(bot.getIdOfUpdate(any(Update.class))).thenReturn(1L);
        when(bot.getLastUpdate()).thenReturn(new Update());
    }

    @Test
    void testCanHandle() {
        UntrackCommand command = new UntrackCommand();
        assertTrue(command.canHandle("/untrack"));
        assertFalse(command.canHandle("/start"));
    }

    @Test
    void testWithIncorrectLink() {
        UntrackCommand command = new UntrackCommand();
        when(bot.getMessageOfUpdate(any(Update.class))).thenReturn("message");

        command.processRequest(update, bot);

        verify(bot, times(2)).execute(any(SendMessage.class));
    }

    @Test
    void testWithCorrectUsedLink() {
        UntrackCommand command = new UntrackCommand();
        when(bot.getMessageOfUpdate(any(Update.class))).thenReturn("https://www.google.com/");
        when(bot.removeLinkFromTrack(any(String.class), any(Resource.class))).thenReturn(false);

        command.processRequest(update, bot);

        verify(bot, times(2)).execute(any(SendMessage.class));
    }

    @Test
    void testWithCorrectUnusedLink() {
        UntrackCommand command = new UntrackCommand();
        when(bot.getMessageOfUpdate(any(Update.class))).thenReturn("https://www.google.com/");
        when(bot.removeLinkFromTrack(any(String.class), any(Resource.class))).thenReturn(true);

        command.processRequest(update, bot);

        verify(bot, times(2)).execute(any(SendMessage.class));
    }
}
