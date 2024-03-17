package edu.java.scheduler;

import edu.java.client.BotClient;
import edu.java.client.StackOverflowClient;
import edu.java.domain.jdbc.LinkDAO;
import edu.java.dto.LinkUpdateRequest;
import edu.java.dto.StackOverflowUpdate;
import edu.java.dto.dbDTO.Link;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StackOverFlowLinkUpdater implements LinkUpdater {
    private final BotClient botClient;
    private final StackOverflowClient stackOverflowClient;
    private final LinkDAO linkDAO;

    @Override
    public int update(Link link) {
        String url = link.getLinkName();
        String[] parts = url.split("/");
        int question = Integer.parseInt(parts[parts.length - 1]);
        StackOverflowUpdate stackOverflowResponse = stackOverflowClient.getQuestionInfo(question).block();
        var updatedAt = stackOverflowResponse.getQuestions().getFirst().getUpdatedAt();
        if (updatedAt.isAfter(link.getLastUpdate().toInstant()
            .atOffset(ZoneOffset.UTC))) {
            linkDAO.updateLastUpdate(Timestamp.from(updatedAt.toInstant()), url);
            LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(link.getId(), url,
                "Update on link " + url, linkDAO.findAllChatsByLink(link).toArray(new Long[0])
            );
            botClient.sendUpdate(linkUpdateRequest);
            return 1;
        }
        return 0;
    }
}
