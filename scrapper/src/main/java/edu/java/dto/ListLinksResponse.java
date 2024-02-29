package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ListLinksResponse(
    @JsonProperty("links")
    LinkResponse[] linkResponses,
    @JsonProperty("size")
    int size
) {
}
