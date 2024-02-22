package edu.java.client;

import edu.java.dto.StackOverflowUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StackOverflowClient {
    private final WebClient stackOverflowWebClient;

    public Mono<StackOverflowUpdate> getQuestionInfo(long id) {
        return stackOverflowWebClient.get()
            .uri("/questions/{id}?site=stackoverflow", id)
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                clientResponse -> Mono.error(new Exception("exception"))
            )
            .bodyToMono(StackOverflowUpdate.class);
    }
}
