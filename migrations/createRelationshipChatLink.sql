CREATE TABLE IF NOT EXISTS ChatLink (
    chat_id BIGINT,
    link_id BIGINT,
    PRIMARY KEY (chat_id, link_id),
    FOREIGN KEY (chat_id) REFERENCES Chat(chat_id),
    FOREIGN KEY (link_id) REFERENCES Link(link_id)
);
