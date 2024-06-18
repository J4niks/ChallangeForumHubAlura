CREATE TABLE respostas (
    id BIGSERIAL PRIMARY KEY,
    message VARCHAR(300) NOT NULL UNIQUE,
    creation_date TIMESTAMP NOT NULL,
    soluction BOOLEAN,
    topico_id BIGINT,

    FOREIGN KEY (topico_id) REFERENCES topicos(id) ON DELETE CASCADE
);