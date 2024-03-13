package edu.java.bot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddLinkRequest(
    @JsonProperty("link")
    String link
) {
}
