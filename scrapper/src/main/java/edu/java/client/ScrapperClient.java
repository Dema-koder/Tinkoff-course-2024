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
    private final WebClient webClient;

    public Mono<String> addLinkToList(int id) {
        String path = "/tg-chat/" + id;
        return webClient.post()
            .uri(path)
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.getExceptionMessage()))))
            .bodyToMono(String.class);
    }

    public Mono<String> deleteChatById(int id) {
        String path = "/tg-chat/" + id;
        return webClient.post()
            .uri(path)
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.getExceptionMessage()))))
            .bodyToMono(String.class);
    }

    public Mono<LinkResponse> addLinkToList(int id, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri("links")
            .body(BodyInserters.fromValue(addLinkRequest))
            .header("Tg-Chat-Id", String.valueOf(id))
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.getExceptionMessage()))))
            .bodyToMono(LinkResponse.class);
    }

    public Mono<LinkResponse> removeLinkFromList(int id, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri("links")
            .body(BodyInserters.fromValue(removeLinkRequest))
            .header("Tg-Chat-Id", String.valueOf(id))
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.getExceptionMessage()))))
            .bodyToMono(LinkResponse.class);
    }

    public Mono<ListLinksResponse> getListOfLinks(long id) {
        return webClient.get()
            .uri("links")
            .header("Tg-Chat-Id", String.valueOf(id))
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse.getExceptionMessage()))))
            .bodyToMono(ListLinksResponse.class);
    }
}
