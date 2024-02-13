package edu.java.bot.Commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class StartCommandTest {
    private Update update;
    private Bot bot;

    @BeforeEach
    void setUp() {
        update = mock(Update.class);
        bot = mock(Bot.class);

        when(bot.getIdOfUpdate(any(Update.class))).thenReturn(1L);
        when(bot.getUsernameOfUpdate(any(Update.class))).thenReturn("user");
    }

    @Test
    void testCanHandle() {
        StartCommand command = new StartCommand();
        assertTrue(command.canHandle("/start"));
        assertFalse(command.canHandle("/list"));
    }

    @Test
    void testFirstRegistration() {
        StartCommand command = new StartCommand();
        when(bot.addUserToDB("user")).thenReturn(true);

        command.processRequest(update, bot);

        verify(bot).execute(any(SendMessage.class));
    }

    @Test
    void testNotFirstRegistration() {
        StartCommand command = new StartCommand();
        when(bot.addUserToDB("user")).thenReturn(false);

        command.processRequest(update, bot);

        verify(bot).execute(any(SendMessage.class));
    }
}
