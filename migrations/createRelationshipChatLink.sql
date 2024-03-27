CREATE TABLE chat_to_link
(
    chat_id BIGINT REFERENCES chat(id) ON DELETE CASCADE,
    link_id BIGINT REFERENCES link(id) ON DELETE CASCADE,
    PRIMARY KEY(chat_id, link_id)
);
