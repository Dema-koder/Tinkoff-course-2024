package edu.java.services;

import edu.java.domain.LinkDAO;
import edu.java.dto.dbDTO.Link;
import edu.java.scheduler.GithubLinkUpdater;
import edu.java.scheduler.StackOverFlowLinkUpdater;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImplLinkUpdaterService implements LinkUpdaterService {
    private final LinkDAO linkDAO;
    private final GithubLinkUpdater gitHubLinkUpdater;
    private final StackOverFlowLinkUpdater stackOverflowLinkUpdater;

    @Override
    public int update() {
        List<Link> links = linkDAO.findAllLink();
        int result = 0;
        for (var link: links) {
            if (link.getLinkName().startsWith("https://github.com")) {
                result += gitHubLinkUpdater.update(link);
            }
            if (link.getLinkName().startsWith("https://stackoverflow.com")) {
                result += stackOverflowLinkUpdater.update(link);
            }
        }
        return result;
    }
}
