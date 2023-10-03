select
    t.description,
    t.id,
    t.title,
    t.user_id
from
    task t;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL
);


CREATE TABLE task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
