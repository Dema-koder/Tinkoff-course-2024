package edu.java.controllers;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import edu.java.dto.dbDTO.Link;
import edu.java.exceptions.ChatDoesNotExistException;
import edu.java.exceptions.WrongParametersException;
import edu.java.services.LinkService;
import edu.java.services.TgChatService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ScrapperController {
    private final TgChatService tgChatService;
    private final LinkService linkService;

    @DeleteMapping("/links")
    public LinkResponse removeLinkFromList(long tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest)
        throws ChatDoesNotExistException, WrongParametersException {
        Link link = linkService.remove(tgChatId, URI.create(removeLinkRequest.link()));
        return new LinkResponse(link.getId(), link.getLinkName());
    }

    @ApiResponse(responseCode = "200", description = "Чат удален")
    @DeleteMapping("/tg-chat/{id}")
    public String deleteChatById(@PathVariable Long id) throws ChatDoesNotExistException {
        tgChatService.unregister(id);
        return "Чат удален";
    }

    @ApiResponse(responseCode = "200", description = "Ссылки успешно получены")
    @GetMapping("/links")
    public ListLinksResponse getListOfLinks(Long tgChatId) throws ChatDoesNotExistException {
        List<Link> links = linkService.listAll(tgChatId);
        List<LinkResponse> listLinksResponse = new ArrayList<>();
        for (Link link: links) {
            listLinksResponse.add(new LinkResponse(link.getId(), link.getLinkName()));
        }
        return new ListLinksResponse(listLinksResponse.toArray(LinkResponse[]::new), listLinksResponse.size());
    }

    @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена")
    @PostMapping("/links")
    public LinkResponse addLinkToList(Long tgChatId, @RequestBody AddLinkRequest addLinkRequest)
        throws ChatDoesNotExistException, WrongParametersException {
        Link link = linkService.add(tgChatId, URI.create(addLinkRequest.link()));
        return new LinkResponse(link.getId(), link.getLinkName());
    }

    @ApiResponse(responseCode = "200", description = "Чат зарегестрирован")
    @PostMapping("/tg-chat/{id}")
    public String getChatById(@PathVariable Long id) {
        tgChatService.register(id);
        return "Чат Зарегестрирован";
    }
}
