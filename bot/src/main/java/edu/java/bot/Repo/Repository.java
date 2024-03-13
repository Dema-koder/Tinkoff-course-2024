package edu.java.bot.Repo;

import edu.java.bot.Resources.Resource;

public interface Repository {
    boolean addUser(String login);

    boolean addLink(String login, Resource resource);

    boolean removeLink(String login, Resource resource);

    String getListOfLinks(String login);
}
