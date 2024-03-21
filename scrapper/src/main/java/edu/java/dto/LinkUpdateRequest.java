package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LinkUpdateRequest(
    @JsonProperty("id")
    Long id,
    @JsonProperty("url")
    String url,
    @JsonProperty("description")
    String description,
    @JsonProperty("tgChatIds")
    Long[] tgChatIds
) {
}
