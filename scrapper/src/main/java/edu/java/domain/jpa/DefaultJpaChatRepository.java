package edu.java.domain.jpa;

import edu.java.domain.ChatRepository;
import edu.java.dto.dbDTO.Chat;
import edu.java.dto.dbDTO.Link;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class DefaultJpaChatRepository implements ChatRepository {
    private final JpaChatRepository jpaChatRepository;

    @Override
    @Transactional
    public void add(long id) {
        Optional<Chat> chat = findById(id);
        jpaChatRepository.save(new Chat(id));
    }

    @Override
    @Transactional
    public int remove(long id) {
        Chat chat = findById(id).get();
        List<Link> links = chat.getLinks();
        if (links != null) {
            links.clear();
        }
        jpaChatRepository.deleteNotUsedLinks();
        return jpaChatRepository.deleteChatById(id);
    }

    @Override
    public Optional<Chat> findById(long id) {
        return jpaChatRepository.findById(id);
    }

    @Override
    public List<Chat> findAll() {
        return jpaChatRepository.findAll();
    }
}
