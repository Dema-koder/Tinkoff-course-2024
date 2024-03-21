CREATE TABLE IF NOT EXISTS link (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    link_name VARCHAR(1000) NOT NULL,
    last_check TIMESTAMP,
    last_update TIMESTAMP,
    last_commit TIMESTAMP,
    answer_count INT DEFAULT -1,
    type varchar(31)
);
