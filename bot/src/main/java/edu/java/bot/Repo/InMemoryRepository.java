package edu.java.bot.Repo;

import edu.java.bot.Resources.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryRepository implements Repository {
    private Map<String, Set<Resource>> dataBase;

    public InMemoryRepository() {
        this.dataBase = new HashMap<>();
    }

    @Override
    public boolean addUser(String login) {
        if (dataBase.containsKey(login)) {
            return false;
        }
        dataBase.put(login, new HashSet<>());
        return true;
    }

    @Override
    public boolean addLink(String login, Resource resource) {
        if (dataBase.get(login).contains(resource)) {
            return false;
        }
        dataBase.get(login).add(resource);
        return true;
    }

    @Override
    public boolean removeLink(String login, Resource resource) {
        boolean check = false;
        Resource cur = new Resource();
        for (var res : dataBase.get(login)) {
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

    @Override
    public String getListOfLinks(String login) {
        String links = dataBase.get(login).stream()
            .map(Object::toString)
            .collect(Collectors.joining("\n"));
        return links;
    }
}
