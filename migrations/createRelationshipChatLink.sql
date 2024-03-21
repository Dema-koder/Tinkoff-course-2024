CREATE TABLE chat_to_link
(
    chat_id BIGINT REFERENCES chat(id),
    link_id BIGINT REFERENCES link(id),
    PRIMARY KEY(chat_id, link_id)
);
