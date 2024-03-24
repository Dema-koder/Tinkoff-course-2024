package edu.java.scheduler;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.domain.jdbc.LinkDAO;
import edu.java.dto.GitHubUpdate;
import edu.java.dto.dbDTO.Link;
import org.junit.Test;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GithubLinkUpdaterTest {
    @Test
    public void checkLastUpdateTest() {
        BotClient botClient = mock(BotClient.class);
        GitHubClient gitHubClient = mock(GitHubClient.class);
        LinkDAO linkDAO = mock(LinkDAO.class);
        Link link = new Link(
            1L,
            "https://github.com",
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            0,
            "GithubLink"
        );
        GitHubUpdate gitHubUpdate = new GitHubUpdate();
        gitHubUpdate.setUpdatedAt(OffsetDateTime.MAX);
        GithubLinkUpdater githubLinkUpdater = new GithubLinkUpdater(botClient, gitHubClient, linkDAO);
        int ans = githubLinkUpdater.checkLastUpdate(link, gitHubUpdate);
        verify(linkDAO).findAllChatsByLink(any());
        doNothing().when(linkDAO).updateLastUpdate(Timestamp.from(OffsetDateTime.MAX.toInstant()), "https://github.com");
        verify(botClient).sendUpdate(any());
        assertThat(ans).isEqualTo(1);
    }

    @Test
    public void checkLastCommitTest() {
        BotClient botClient = mock(BotClient.class);
        GitHubClient gitHubClient = mock(GitHubClient.class);
        LinkDAO linkDAO = mock(LinkDAO.class);
        Link link = new Link(
            1L,
            "https://github.com",
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            0,
            "GithubLink"
        );
        GitHubUpdate gitHubUpdate = new GitHubUpdate();
        gitHubUpdate.setPushedAt(OffsetDateTime.MAX);

        GithubLinkUpdater githubLinkUpdater = new GithubLinkUpdater(botClient, gitHubClient, linkDAO);
        githubLinkUpdater.checkLastCommit(link, gitHubUpdate);

        verify(botClient).sendUpdate(any());
    }
}
