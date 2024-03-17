package edu.java.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.dto.LinkUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 8090)
class BotClientTest {
    private final BotClient botClient = new BotClient(WebClient.create("http://localhost:8090"));

    @Test
    void sendUpdateTest() {
        stubFor(post(urlEqualTo("/updates"))
            .willReturn(aResponse()
                .withStatus(200)));

        StepVerifier.create(botClient.sendUpdate(new LinkUpdateRequest(1L, "url", "desc", new Long[0])))
            .assertNext(response -> {
                assertThat(response.equals("Обновление обработано"));
            });
    }
}
