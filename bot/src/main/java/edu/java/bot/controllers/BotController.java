package edu.java.bot.controllers;

import edu.java.bot.dtos.LinkUpdateRequest;
import edu.java.bot.exceptions.WrongParametersException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    @ApiResponse(responseCode = "200", description = "Обновление обработано")
    @PostMapping("/updates")
    public String getUpdates(@RequestBody LinkUpdateRequest linkUpdateRequest) throws WrongParametersException {
        if (linkUpdateRequest == null) {
            throw new WrongParametersException("Некорректные параметры запроса");
        }
        return "Обновление обработано";
    }
}
