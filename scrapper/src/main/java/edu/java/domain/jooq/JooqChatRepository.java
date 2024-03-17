package edu.java.domain.jooq;

import edu.java.domain.ChatRepository;
import edu.java.domain.jooq.generation2.Tables;
import edu.java.dto.dbDTO.Chat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JooqChatRepository implements ChatRepository {
    private final DSLContext dslContext;

    @Override
    public void add(long id) {
        dslContext
                .insertInto(Tables.CHAT)
                .set(Tables.CHAT.ID, id)
                .execute();
    }

    @Override
    public int remove(long id) {
        List<Long> links = dslContext
                .select(Tables.CHAT_TO_LINK.LINK_ID)
                .from(Tables.CHAT_TO_LINK)
                .where(Tables.CHAT_TO_LINK.CHAT_ID.eq(id))
                .fetchInto(Long.class);
        dslContext
                .deleteFrom(Tables.CHAT_TO_LINK)
                .where(Tables.CHAT_TO_LINK.CHAT_ID.eq(id))
                .execute();
        for (Long linkId : links) {
            List<Long> chatsWithSameLink = dslContext
                    .select(Tables.CHAT_TO_LINK.CHAT_ID)
                    .from(Tables.CHAT_TO_LINK)
                    .where(Tables.CHAT_TO_LINK.LINK_ID.eq(linkId))
                    .fetchInto(Long.class);
            if (chatsWithSameLink.isEmpty()) {
                dslContext
                        .deleteFrom(Tables.LINK)
                        .where(Tables.LINK.ID.eq(linkId));
            }
        }
        return dslContext
                .deleteFrom(Tables.CHAT)
                .where(Tables.CHAT.ID.eq(id))
                .execute();
    }

    @Override
    public Optional<Chat> findById(long id) {
        List<Chat> chats = dslContext
                .selectFrom(Tables.CHAT)
                .where(Tables.CHAT.ID.eq(id))
                .fetchInto(Chat.class);
        if (chats.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(chats.getFirst());
    }

    @Override
    public List<Chat> findAll() {
        return dslContext
                .selectFrom(Tables.CHAT)
                .fetchInto(Chat.class);
    }
}
