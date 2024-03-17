package edu.java.services.jdbc;

import edu.java.domain.ChatDAO;
import edu.java.domain.LinkDAO;
import edu.java.dto.dbDTO.Link;
import edu.java.exceptions.ChatDoesNotExistException;
import edu.java.exceptions.WrongParametersException;
import edu.java.services.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private static final String NO_CHAT = "Чата не существует";
    private final LinkDAO linkDAO;
    private final ChatDAO chatDAO;

    @Override
    public Link add(Long tgChatId, URI url) throws ChatDoesNotExistException, WrongParametersException {
        if (chatDAO.findById(tgChatId).isEmpty()) {
            throw new ChatDoesNotExistException(NO_CHAT);
        }
        Optional<Link> link = linkDAO.findByChatIdAndUrl(tgChatId, url.toString());
        if (link.isPresent()) {
            throw new WrongParametersException("Ссылку уже есть в базе данных");
        }
        return linkDAO.addLink(tgChatId, url.toString(), new Timestamp(1L));
    }

    @Override
    public Link remove(Long tgChatId, URI url) throws WrongParametersException {
        if (chatDAO.findById(tgChatId).isEmpty()) {
            throw new WrongParametersException(NO_CHAT);
        }
        Optional<Link> link = linkDAO.findByChatIdAndUrl(tgChatId, url.toString());
        if (link.isPresent()) {
            return linkDAO.removeLink(tgChatId, url.toString());
        }
        throw new WrongParametersException("Нет нужной ссылки");
    }

    @Override
    public List<Link> listAll(Long tgChatId) throws ChatDoesNotExistException {
        if (chatDAO.findById(tgChatId).isEmpty()) {
            throw new ChatDoesNotExistException(NO_CHAT);
        }
        return linkDAO.findAllLinksByTgId(tgChatId);
    }
}
