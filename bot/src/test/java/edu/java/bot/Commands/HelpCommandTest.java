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

class HelpCommandTest {
    private Update update;
    private Bot bot;

    @BeforeEach
    void setUp() {
        update = mock(Update.class);
        bot = mock(Bot.class);
    }

    @Test
    void testCanHandle() {
        HelpCommand command = new HelpCommand();
        assertTrue(command.canHandle("/help"));
        assertFalse(command.canHandle("/start"));
    }

    @Test
    void testProcessRequest() {
        HelpCommand command = new HelpCommand();
        when(bot.getIdOfUpdate(any(Update.class))).thenReturn(1L);

        command.processRequest(update, bot);

        verify(bot).execute(any(SendMessage.class));
    }
}
