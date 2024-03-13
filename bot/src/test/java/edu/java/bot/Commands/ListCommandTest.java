package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListCommandTest {
    private Update update;
    private Bot bot;

    @BeforeEach
    void setUp() {
        update = mock(Update.class);
        bot = mock(Bot.class);
    }

    @Test
    void testCanHandle() {
        ListCommand command = new ListCommand();
        assertTrue(command.canHandle("/list"));
        assertFalse(command.canHandle("/start"));
    }

    @Test
    void testWithEmptyBD() {
        ListCommand command = new ListCommand();
        when(bot.getUsernameOfUpdate(any(Update.class))).thenReturn("user");
        when(bot.getIdOfUpdate(any(Update.class))).thenReturn(1L);
        when(bot.getListOfLinks("user")).thenReturn("");

        command.processRequest(update, bot);

        verify(bot).execute(any(SendMessage.class));
    }

    @Test
    void testWithNotEmptyBD() {
        ListCommand command = new ListCommand();
        when(bot.getUsernameOfUpdate(any(Update.class))).thenReturn("user");
        when(bot.getIdOfUpdate(any(Update.class))).thenReturn(1L);
        when(bot.getListOfLinks("user")).thenReturn("track");

        command.processRequest(update, bot);

        verify(bot).execute(any(SendMessage.class));
    }
}
