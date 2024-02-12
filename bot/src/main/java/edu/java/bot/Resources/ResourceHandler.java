package edu.java.bot.Resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ResourceHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private ResourceHandler nextHandler;

    public void setNextHandler(ResourceHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handleRequest(Resource resource) {
        if (canHandle(resource)) {
            processRequest(resource);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(resource);
        } else {
            LOGGER.error("нет нужного обработчика");
        }
    }

    protected abstract boolean canHandle(Resource resourse);

    protected abstract void processRequest(Resource resource);
}
