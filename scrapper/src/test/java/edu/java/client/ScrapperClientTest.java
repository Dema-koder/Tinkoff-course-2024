package edu.java.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.dto.AddLinkRequest;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest(httpPort = 8080)
class ScrapperClientTest {
    private final ScrapperClient scrapperClient =
        new ScrapperClient(WebClient.create("http://localhost:8080"));

    @Test
    void userRegistrationTest() {
        stubFor(post(urlEqualTo("/tg-chat/1"))
            .willReturn(aResponse()
                .withStatus(200)));

        StepVerifier.create(scrapperClient.userRegistration(1))
            .assertNext(response -> {
                assertThat(response.equals("Чат Зарегестрирован"));
            });
    }

    @Test
    void deleteChatByIdTest() {
        stubFor(post(urlEqualTo("/tg-chat/1"))
            .willReturn(aResponse()
                .withStatus(200)));

        StepVerifier.create(scrapperClient.deleteChatById(1))
            .assertNext(response -> {
                assertThat(response.equals("Чат Удален"));
            });
    }

    @Test
    void addLinkToListTest() {
        stubFor(post(urlEqualTo("/links?tgChatId=1"))
            .willReturn(aResponse()
                .withStatus(200)));

        StepVerifier.create(scrapperClient.addLinkToList(1, new AddLinkRequest("link")))
            .assertNext(response -> {
                assertThat(response.url().equals("string"));
            });
    }

    @Test
    void removeLinkFromListTest() {
        stubFor(post(urlEqualTo("/links?tgChatId=1"))
            .willReturn(aResponse()
                .withStatus(200)));

        StepVerifier.create(scrapperClient.removeLinkFromList(1, new RemoveLinkRequest("string")))
            .assertNext(response -> {
                assertThat(response.url().equals("string"));
            });
    }

    @Test
    void getListOfLinksTest() {
        stubFor(post(urlEqualTo("/links?tgChatId=1"))
            .willReturn(aResponse()
                .withStatus(200)));

        StepVerifier.create(scrapperClient.getListOfLinks(1))
            .assertNext(response -> {
                assertThat(response.size() == 0);
            });
    }
}
