package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import edu.java.bot.Resources.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackCommandTest {
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
        TrackCommand command = new TrackCommand();
        assertTrue(command.canHandle("/track"));
        assertFalse(command.canHandle("/start"));
    }

    @Test
    void testWithIncorrectLink() {
        TrackCommand command = new TrackCommand();
        when(bot.getMessageOfUpdate(any(Update.class))).thenReturn("message");

        command.processRequest(update, bot);

        verify(bot, times(2)).execute(any(SendMessage.class));
    }

    @Test
    void testWithCorrectUsedLink() throws MalformedURLException {
        TrackCommand command = new TrackCommand();
        when(bot.getMessageOfUpdate(any(Update.class))).thenReturn("https://www.google.com/");
        when(bot.addLinkToTrack(any(String.class), any(Resource.class))).thenReturn(false);

        command.processRequest(update, bot);

        verify(bot, times(2)).execute(any(SendMessage.class));
    }

    @Test
    void testWithCorrectUnusedLink() throws MalformedURLException {
        TrackCommand command = new TrackCommand();
        when(bot.getMessageOfUpdate(any(Update.class))).thenReturn("https://www.google.com/");
        when(bot.addLinkToTrack(any(String.class), any(Resource.class))).thenReturn(true);

        command.processRequest(update, bot);

        verify(bot, times(2)).execute(any(SendMessage.class));
    }
}
