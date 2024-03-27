package edu.java.scheduler;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.domain.LinkDAO;
import edu.java.dto.GitHubUpdate;
import edu.java.dto.LinkUpdateRequest;
import edu.java.dto.dbDTO.Link;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubLinkUpdater implements LinkUpdater {
    private final BotClient botClient;
    private final GitHubClient gitHubClient;
    private final LinkDAO linkDAO;

    @Override
    public int update(Link link) {
        String url = link.getLinkName();
        String[] parts = url.split("/");
        String owner = parts[parts.length - 2];
        String repository = parts[parts.length - 1];
        GitHubUpdate gitHubUpdate = gitHubClient.getRepositoryInfo(owner, repository).block();
        var listOfChats = linkDAO.findAllChatsByLink(link);
        if (gitHubUpdate.updatedAt.isAfter(link.getLastUpdate().toInstant().atOffset(ZoneOffset.UTC))) {
            linkDAO.updateLastUpdate(Timestamp.from(gitHubUpdate.updatedAt.toInstant()), url);
            LinkUpdateRequest linkUpdateRequest =
                new LinkUpdateRequest(link.getId(), url, "Обновление в " + url, listOfChats.toArray(new Long[0]));
            botClient.sendUpdate(linkUpdateRequest);
            return 1;
        }
        return 0;
    }
}
