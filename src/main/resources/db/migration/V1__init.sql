BEGIN;
SET search_path TO hiber,public;
DROP TABLE IF EXISTS books CASCADE;
CREATE TABLE books (
    id                  bigserial PRIMARY KEY,
    title               VARCHAR(255),
    cost                numeric(6, 2),
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);

INSERT INTO books (title, cost)
VALUES
('Tom Soyer', 29.90),
('Harry Potter', 24.90),
('Sherlock Holmes', 22.90),
('Dorian Gray', 32.90);

COMMIT