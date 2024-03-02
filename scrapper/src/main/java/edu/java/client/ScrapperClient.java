package edu.java.client;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.ApiErrorResponse;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ScrapperClient {
    private static final String CHATS = "/tg-chat/";
    private static final String LINKS = "links";
    private static final String HEAD = "Tg-Chat-Id";
    private final WebClient scrapperWebClient;

    public Mono<String> userRegistration(int id) {
        String path = CHATS + id;
        return scrapperWebClient.post()
            .uri(path)
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.getExceptionMessage()))))
            .bodyToMono(String.class);
    }

    public Mono<String> deleteChatById(int id) {
        String path = CHATS + id;
        return scrapperWebClient.post()
            .uri(path)
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.getExceptionMessage()))))
            .bodyToMono(String.class);
    }

    public Mono<LinkResponse> addLinkToList(int id, AddLinkRequest addLinkRequest) {
        return scrapperWebClient.post()
            .uri(LINKS)
            .body(BodyInserters.fromValue(addLinkRequest))
            .header(HEAD, String.valueOf(id))
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.getExceptionMessage()))))
            .bodyToMono(LinkResponse.class);
    }

    public Mono<LinkResponse> removeLinkFromList(int id, RemoveLinkRequest removeLinkRequest) {
        return scrapperWebClient.method(HttpMethod.DELETE)
            .uri(LINKS)
            .body(BodyInserters.fromValue(removeLinkRequest))
            .header(HEAD, String.valueOf(id))
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.getExceptionMessage()))))
            .bodyToMono(LinkResponse.class);
    }

    public Mono<ListLinksResponse> getListOfLinks(long id) {
        return scrapperWebClient.get()
            .uri(LINKS)
            .header(HEAD, String.valueOf(id))
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.getExceptionMessage()))))
            .bodyToMono(ListLinksResponse.class);
    }
}
