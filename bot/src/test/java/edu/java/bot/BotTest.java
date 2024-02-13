package edu.java.bot;

import edu.java.bot.Resources.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class BotTest {
    private Bot bot;

    @BeforeEach
    void setUp() {
        bot = new Bot("your_token");
    }

    @Test
    void testAddUserToDB() {
        assertTrue(bot.addUserToDB("user1"));
        assertFalse(bot.addUserToDB("user1"));
    }

    @Test
    void testGetListOfLinks() throws MalformedURLException {
        Map<String, Set<Resource>> dataBase = new HashMap<>();
        Set<Resource> links = new HashSet<>();
        links.add(new Resource("https://example.com"));
        dataBase.put("login", links);
        bot.setDataBase(dataBase);

        String result = bot.getListOfLinks("login");

        assert result.equals("https://example.com");
    }

    @Test
    void testAddLinkToTrack() {
        bot.addUserToDB("user1");
        Resource resource = new Resource();
        try {
            resource = new Resource("https://example.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(bot.addLinkToTrack("user1", resource));
        assertFalse(bot.addLinkToTrack("user1", resource));
    }

    @Test
    void testRemoveLinkFromTrack() {
        bot.addUserToDB("user1");
        Resource resource = new Resource();
        try {
            resource = new Resource("https://example.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        bot.addLinkToTrack("user1", resource);
        assertTrue(bot.removeLinkFromTrack("user1", resource));
        assertFalse(bot.removeLinkFromTrack("user1", resource));
    }
}
