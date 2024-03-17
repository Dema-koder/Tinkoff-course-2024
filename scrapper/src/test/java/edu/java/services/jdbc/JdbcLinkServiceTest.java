package edu.java.services.jdbc;

import edu.java.domain.jdbc.ChatDAO;
import edu.java.domain.jdbc.LinkDAO;
import edu.java.dto.dbDTO.Chat;
import edu.java.dto.dbDTO.Link;
import edu.java.exceptions.ChatDoesNotExistException;
import edu.java.exceptions.WrongParametersException;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JdbcLinkServiceTest {
    @Test
    public void addTest() throws WrongParametersException, ChatDoesNotExistException {
        LinkDAO linkDAO = mock(LinkDAO.class);
        ChatDAO chatDAO = mock(ChatDAO.class);
        String url = "https://example.com";
        JdbcLinkService jdbcLinkService = new JdbcLinkService(linkDAO, chatDAO);
        when(chatDAO.findById(1L)).thenReturn(Optional.of(new Chat()));
        when(linkDAO.findByChatIdAndUrl(1L, url)).thenReturn(Optional.empty());
        Timestamp timestamp = new Timestamp(1L);
        Link expected = new Link(1L, url, timestamp);
        when(linkDAO.addLink(1L, url, timestamp)).thenReturn(expected);
        Link response = jdbcLinkService.add(1L, URI.create(url));
        assertThat(expected).isEqualTo(response);
    }

    @Test
    public void removeTest() throws WrongParametersException {
        LinkDAO linkDAO = mock(LinkDAO.class);
        ChatDAO chatDAO = mock(ChatDAO.class);
        String url = "https://example.com";
        Timestamp timestamp = new Timestamp(1L);
        Link expected = new Link(1L, url, timestamp);
        JdbcLinkService jdbcLinkService = new JdbcLinkService(linkDAO, chatDAO);
        when(chatDAO.findById(1L)).thenReturn(Optional.of(new Chat()));
        when(linkDAO.findByChatIdAndUrl(1L, url)).thenReturn(Optional.of(expected));
        when(linkDAO.removeLink(1L, url)).thenReturn(expected);
        Link response = jdbcLinkService.remove(1L, URI.create(url));
        assertThat(expected).isEqualTo(response);
    }

    @Test
    public void testGetLinks() throws ChatDoesNotExistException {
        LinkDAO linkDAO = mock(LinkDAO.class);
        ChatDAO chatDAO = mock(ChatDAO.class);
        URI uri1 = URI.create("https://exmaple1.com");
        URI uri2 = URI.create("https://example2.com");
        Timestamp timestamp = new Timestamp(1L);
        Link expected1 = new Link(1L, uri1.toString(), timestamp);
        Link expected2 = new Link(1L, uri2.toString(), timestamp);
        JdbcLinkService jdbcLinkService = new JdbcLinkService(linkDAO, chatDAO);
        when(chatDAO.findById(1L)).thenReturn(Optional.of(new Chat()));
        when(linkDAO.findAllLinksByTgId(1L)).thenReturn(List.of(expected1, expected2));

        List<Link> response = jdbcLinkService.listAll(1L);
        assertThat(2).isEqualTo(response.size());
        assertThat(expected1).isEqualTo(response.getFirst());
        assertThat(expected2).isEqualTo(response.getLast());
    }
}
