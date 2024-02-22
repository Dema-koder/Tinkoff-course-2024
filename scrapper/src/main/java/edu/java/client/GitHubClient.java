package edu.java.client;

import edu.java.dto.GitHubUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GitHubClient {
    private final WebClient githubWebClient;

    public Mono<GitHubUpdate> getRepositoryInfo(String owner, String repositoryName) {
        return githubWebClient.get()
            .uri("/repos/{owner}/{repositoryName}", owner, repositoryName)
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                clientResponse -> Mono.error(new Exception("exception"))
            )
            .bodyToMono(GitHubUpdate.class);
    }
}