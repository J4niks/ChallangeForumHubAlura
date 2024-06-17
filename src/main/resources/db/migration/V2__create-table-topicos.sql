CREATE EXTENSION IF NOT EXISTS "fuzzystrmatch";

CREATE TABLE topicos (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL UNIQUE,
    message VARCHAR(300) NOT NULL UNIQUE,
    creation_date TIMESTAMP NOT NULL,
    status VARCHAR(20),
    course_id BIGINT,

    FOREIGN KEY (course_id) REFERENCES cursos(id) ON DELETE CASCADE
);