BEGIN;

DROP SCHEMA IF EXISTS hiber;
CREATE SCHEMA hiber;
SET search_path TO hiber,public;
DROP TABLE IF EXISTS books CASCADE;

CREATE TABLE books (
    id                  BIGSERIAL PRIMARY KEY,
    title               VARCHAR(255) NOT NULL,
    price               NUMERIC(8, 2),
    description         TEXT,
    year_of_publish     INT,
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

CREATE TABLE books_info (
    id                  BIGSERIAL PRIMARY KEY,
    book_id             BIGINT NOT NULL REFERENCES books (id),
    size                INT NOT NULL,
    score               NUMERIC(2, 1),
    age_recommendation  INT
);

CREATE TABLE books_storage (
    id                  BIGSERIAL PRIMARY KEY,
    book_id             BIGINT NOT NULL REFERENCES books (id),
    link_cover          VARCHAR(255),
    link_fb2            VARCHAR(255),
    link_pdf            VARCHAR(255),
    link_doc            VARCHAR(255),
    link_audio          VARCHAR(255)
);

CREATE TABLE authors (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    date_of_birth       DATE,
    biography           TEXT,
    country             VARCHAR(50)
);

CREATE TABLE books_authors (
    book_id             BIGINT NOT NULL REFERENCES books (id),
    author_id           BIGINT NOT NULL REFERENCES authors (id),
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE genres (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(255) NOT NULL
);

CREATE TABLE books_genres (
    book_id             BIGINT NOT NULL REFERENCES books (id),
    genre_id            BIGINT NOT NULL REFERENCES genres (id),
    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE users (
    id                  BIGSERIAL PRIMARY KEY,
    email               VARCHAR(50) NOT NULL UNIQUE,
    password            VARCHAR(80) NOT NULL,
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

CREATE TABLE users_info (
    id                  BIGSERIAL PRIMARY KEY,
    user_id             BIGINT NOT NULL REFERENCES users (id),
    name                VARCHAR(255),
    phone               VARCHAR(15),
    discount            INT,
    address             VARCHAR(255),
    date_of_birth       DATE
);

CREATE TABLE roles (
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50) NOT NULL UNIQUE,
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

CREATE TABLE users_roles (
    user_id             BIGINT NOT NULL REFERENCES users (id),
    role_id             BIGINT NOT NULL REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE comments (
    id                  BIGSERIAL PRIMARY KEY,
    book_id             BIGINT NOT NULL REFERENCES books (id),
    text                VARCHAR(2000),
    user_id             BIGINT NOT NULL REFERENCES users (id),
    created_at          TIMESTAMP default current_timestamp
);

CREATE TABLE orders (
    id                  BIGSERIAL PRIMARY KEY,
    owner_id            BIGINT NOT NULL REFERENCES users (id),
    total_price         NUMERIC(8, 2),
    address             VARCHAR(255),
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

CREATE TABLE order_items (
    id                  BIGSERIAL PRIMARY KEY,
    order_id            BIGINT REFERENCES orders (id),
    book_id             BIGINT REFERENCES books (id),
    title               VARCHAR(255),
    quantity            INT,
    price_per_book      NUMERIC(8, 2),
    price               NUMERIC(8, 2),
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);

create table carts (
    id                  UUID PRIMARY KEY,
    price               NUMERIC(8, 2)
);

create table cart_items (
    id                  BIGSERIAL PRIMARY KEY,
    cart_id             UUID REFERENCES carts (id),
    book_id             BIGINT REFERENCES books (id),
    title               VARCHAR(255),
    quantity            INT,
    price_per_book      NUMERIC(8, 2),
    price               NUMERIC(8, 2),
    created_at          TIMESTAMP default current_timestamp,
    updated_at          TIMESTAMP default current_timestamp
);


INSERT INTO books (title, price, description, year_of_publish)
VALUES
('Tom Soyer', 29.90, 'Desc 1', 1980),
('Harry Potter', 24.90, 'Desc 2', 1990),
('Sherlock Holmes', 22.90, 'Desc 3', 1995),
('Dorian Gray', 32.90, 'Desc 4', 2005);

COMMIT