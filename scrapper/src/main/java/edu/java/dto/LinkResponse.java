package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LinkResponse(
    @JsonProperty("id")
    Long id,
    @JsonProperty("url")
    String url
) {
}
