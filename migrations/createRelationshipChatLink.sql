CREATE TABLE chat_to_link
(
    chat_id BIGINT REFERENCES Chat(id),
    link_id BIGINT REFERENCES Link(id),
    PRIMARY KEY(chat_id, link_id)
);
