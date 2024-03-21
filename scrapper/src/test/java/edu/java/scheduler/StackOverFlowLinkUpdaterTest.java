package edu.java.scheduler;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.domain.jdbc.LinkDAO;
import edu.java.dto.LinkUpdateRequest;
import edu.java.dto.StackOverflowUpdate;
import edu.java.dto.dbDTO.Link;
import org.junit.Test;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StackOverFlowLinkUpdaterTest {
    @Test
    public void checkUpdatedAtTest() {
        BotClient botClient = mock(BotClient.class);
        StackOverflowClient stackOverflowClient = mock(StackOverflowClient.class);
        LinkDAO linkDAO = mock(LinkDAO.class);
        Link link = new Link(
            1L,
            "https://stackoverflow.com",
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            0,
            "StackoverflowLink"
        );
        StackOverflowUpdate stackOverflowUpdate = new StackOverflowUpdate();
        stackOverflowUpdate.setQuestions(List.of(new StackOverflowUpdate.QuestionResponse()));
        stackOverflowUpdate.getQuestions().getLast().setUpdatedAt(OffsetDateTime.MAX);
        StackOverFlowLinkUpdater stackOverFlowLinkUpdater = new StackOverFlowLinkUpdater(botClient, stackOverflowClient, linkDAO);
        int ans = stackOverFlowLinkUpdater.checkUpdatedAt(link, stackOverflowUpdate);
        doNothing().when(linkDAO).updateLastUpdate(Timestamp.from(OffsetDateTime.MAX.toInstant()), "https://stackoverflow.com");
        verify(botClient).sendUpdate(any());
        assertThat(ans).isEqualTo(1);
    }

    @Test
    public void checkAnswerCountTest() {
        BotClient botClient = mock(BotClient.class);
        StackOverflowClient stackOverflowClient = mock(StackOverflowClient.class);
        LinkDAO linkDAO = mock(LinkDAO.class);
        Link link = new Link(
            1L,
            "https://stackoverflow.com",
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            0,
            "StackoverflowLink"
        );
        StackOverflowUpdate stackOverflowUpdate = new StackOverflowUpdate();
        stackOverflowUpdate.setQuestions(List.of(new StackOverflowUpdate.QuestionResponse()));
        stackOverflowUpdate.getQuestions().getLast().setAnswerCount(1);

        StackOverFlowLinkUpdater stackOverFlowLinkUpdater = new StackOverFlowLinkUpdater(botClient, stackOverflowClient, linkDAO);
        int ans = stackOverFlowLinkUpdater.checkAnswerCount(link, stackOverflowUpdate);
        doNothing().when(linkDAO).updateLastUpdate(Timestamp.from(OffsetDateTime.MAX.toInstant()), "https://stackoverflow.com");
        verify(botClient).sendUpdate(any());
        assertThat(ans).isEqualTo(1);
    }
}
