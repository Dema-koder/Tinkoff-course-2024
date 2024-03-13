package edu.java.bot.Resources;

import java.net.MalformedURLException;
import java.net.URL;

public class Resource {
    private URL url;

    public Resource(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public Resource() {}

    @Override
    public String toString() {
        return this.url.toString();
    }
}
