package edu.java.services;

import edu.java.dto.dbDTO.Link;
import edu.java.exceptions.ChatDoesNotExistException;
import edu.java.exceptions.WrongParametersException;
import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(Long tgChatId, URI url) throws ChatDoesNotExistException, WrongParametersException;

    Link remove(Long tgChatId, URI url) throws WrongParametersException, ChatDoesNotExistException;

    List<Link> listAll(Long tgChatId) throws ChatDoesNotExistException;
}
