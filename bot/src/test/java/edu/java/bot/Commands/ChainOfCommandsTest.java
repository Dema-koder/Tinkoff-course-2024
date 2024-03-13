package edu.java.bot.Commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChainOfCommandsTest {

    @Test
    void isBotCommandTest() {
        ChainOfCommands chain = new ChainOfCommands();
        assertTrue(chain.isBotCommand("/start"));
        assertTrue(chain.isBotCommand("/list"));
        assertTrue(chain.isBotCommand("/track"));
        assertTrue(chain.isBotCommand("/untrack"));
        assertTrue(chain.isBotCommand("/help"));
        assertFalse(chain.isBotCommand("start"));
        assertFalse(chain.isBotCommand("help"));
    }
}
