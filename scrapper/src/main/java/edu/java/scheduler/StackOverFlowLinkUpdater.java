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
        StackOverflowUpdate stackOverflowUpdate = stackOverflowClient.getQuestionInfo(question).block();
        int amount = 0;
        assert stackOverflowUpdate != null;
        amount += checkUpdatedAt(link, stackOverflowUpdate);
        amount += checkAnswerCount(link, stackOverflowUpdate);
        return amount;
    }

    public int checkUpdatedAt(Link link, StackOverflowUpdate stackOverflowUpdate) {
        var url = link.getLinkName();
        var updatedAt = stackOverflowUpdate.getQuestions().getFirst().getUpdatedAt();
        if (updatedAt.isAfter(link.getLastUpdate().toInstant()
            .atOffset(ZoneOffset.UTC))) {
            linkDAO.updateLastUpdate(Timestamp.from(updatedAt.toInstant()), url);
            LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(link.getId(), url,
                "Обновление в " + url, linkDAO.findAllChatsByLink(link).toArray(new Long[0])
            );
            botClient.sendUpdate(linkUpdateRequest);
            return 1;
        }
        return 0;
    }

    public int checkAnswerCount(Link link, StackOverflowUpdate stackOverflowUpdate) {
        var url = link.getLinkName();
        var answerCount = stackOverflowUpdate.getQuestions().getFirst().getAnswerCount();
        if (answerCount != link.getAnswerCount()) {
            linkDAO.updateAnswerCount(url, answerCount);
            LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(link.getId(), url,
                "Новый ответ в " + url, linkDAO.findAllChatsByLink(link).toArray(new Long[0])
            );
            botClient.sendUpdate(linkUpdateRequest);
            return 1;
        }
        return 0;
    }
}
