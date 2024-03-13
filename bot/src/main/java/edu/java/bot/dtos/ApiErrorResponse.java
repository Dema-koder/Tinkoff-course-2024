package edu.java.bot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {
    @JsonProperty("description")
    private String description;
    @JsonProperty("code")
    private String code;
    @JsonProperty("exceptionName")
    private String exceptionName;
    @JsonProperty("exceptionMessage")
    private String exceptionMessage;
    @JsonProperty("stacktrace")
    private String[] stacktrace;
}
