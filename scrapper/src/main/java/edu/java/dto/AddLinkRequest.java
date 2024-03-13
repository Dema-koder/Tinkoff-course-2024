package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddLinkRequest(
    @JsonProperty("link")
    String link
) {
}
