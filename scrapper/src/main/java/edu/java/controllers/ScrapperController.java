package edu.java.controllers;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import edu.java.exceptions.ChatDoesNotExistException;
import edu.java.exceptions.WrongParametersException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ScrapperController {

    private static final String WRONG_PARAMETERS = "Некорректные параметры запроса";
    private static final String CHAT_DOES_NOT_EXIST = "Чат не существует";

    @DeleteMapping("/links")
    public LinkResponse removeLinkFromList(int tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest)
        throws ChatDoesNotExistException, WrongParametersException {
        if (tgChatId == 0) {
            throw new ChatDoesNotExistException(CHAT_DOES_NOT_EXIST);
        }
        if (tgChatId < 0) {
            throw new WrongParametersException(WRONG_PARAMETERS);
        }
        LinkResponse linkResponse = new LinkResponse(0, "string");
        return linkResponse;
    }

    @ApiResponse(responseCode = "200", description = "Чат удален")
    @DeleteMapping("/tg-chat/{id}")
    public String deleteChatById(@PathVariable int id) throws ChatDoesNotExistException, WrongParametersException {
        if (id == 0) { // потом переделаю
            throw new ChatDoesNotExistException(CHAT_DOES_NOT_EXIST);
        }
        if (id < 0) {
            throw new WrongParametersException(WRONG_PARAMETERS);
        }
        log.info("Чат удален");
        return "Чат удален";
    }

    @ApiResponse(responseCode = "200", description = "Ссылки успешно получены")
    @GetMapping("/links")
    public ListLinksResponse getListOfLinks(int tgChatId) throws WrongParametersException {
        ListLinksResponse listLinksResponse = new ListLinksResponse(new LinkResponse[0], 0);
        if (tgChatId < 0) {
            throw new WrongParametersException(WRONG_PARAMETERS);
        }
        return listLinksResponse;
    }

    @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена")
    @PostMapping("/links")
    public LinkResponse addLinkToList(int tgChatId, @RequestBody AddLinkRequest addLinkRequest)
        throws WrongParametersException {
        LinkResponse linkResponse = new LinkResponse(0, "string");
        if (tgChatId < 0 || addLinkRequest == null) {
            throw new WrongParametersException(WRONG_PARAMETERS);
        }
        return linkResponse;
    }

    @ApiResponse(responseCode = "200", description = "Чат зарегестрирован")
    @PostMapping("/tg-chat/{id}")
    public String getChatById(@PathVariable int id) throws WrongParametersException {
        if (id <= 0) {
            throw new WrongParametersException(WRONG_PARAMETERS);
        }
        log.info("Чат зарегестрирован");
        return "Чат Зарегестрирован";
    }


}
