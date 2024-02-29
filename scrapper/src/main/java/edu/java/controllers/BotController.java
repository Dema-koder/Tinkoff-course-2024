package edu.java.controllers;

import edu.java.dto.LinkUpdateRequest;
import edu.java.exceptions.WrongParametersException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BotController {

    @ApiResponse(responseCode = "200", description = "Обновление обработано")
    @PostMapping("/updates")
    public void getUpdates(@RequestBody LinkUpdateRequest linkUpdateRequest) throws WrongParametersException {
        if (linkUpdateRequest == null) {
            throw new WrongParametersException("Некорректные параметры запроса");
        }
        log.info("Обновление обработано");
    }
}
