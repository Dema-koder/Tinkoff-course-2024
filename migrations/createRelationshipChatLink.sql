CREATE TABLE IF NOT EXISTS chat_to_link (
    chat_id BIGINT,
    link_id BIGINT,
    PRIMARY KEY (chat_id, link_id),
    FOREIGN KEY (chat_id) REFERENCES Chat(id),
    FOREIGN KEY (link_id) REFERENCES Link(id)
);
