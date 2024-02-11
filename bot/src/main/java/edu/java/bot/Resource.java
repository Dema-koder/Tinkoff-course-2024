package edu.java.bot;

import java.net.MalformedURLException;
import java.net.URL;

public class Resource {
    private URL url;

    public Resource(String url) throws MalformedURLException {
        this.url = new URL(url);
    }
}
