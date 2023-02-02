DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS books_collection;
DROP TABLE IF EXISTS collection;

CREATE TABLE books
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title     VARCHAR(255),
    author    VARCHAR(255),
    thumbnail VARCHAR(255),
    isbn      VARCHAR(20),
    CONSTRAINT pk_books PRIMARY KEY (id)
);

CREATE TABLE books_collection
(
    collection_id BIGINT  NOT NULL,
    isbn          VARCHAR NOT NULL,
    CONSTRAINT pk_books_collection PRIMARY KEY (collection_id, isbn)
);

CREATE TABLE collection
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name    VARCHAR(255)                            NOT NULL,
    user_id VARCHAR(255),
    CONSTRAINT pk_collection PRIMARY KEY (id)
);

ALTER TABLE books
    ADD CONSTRAINT uc_books_isbn UNIQUE (isbn);

ALTER TABLE books_collection
    ADD CONSTRAINT fk_boocol_on_book_dao FOREIGN KEY (isbn) REFERENCES books (isbn);

ALTER TABLE books_collection
    ADD CONSTRAINT fk_boocol_on_collection FOREIGN KEY (collection_id) REFERENCES collection (id);

CREATE INDEX idx_col_id ON books_collection (collection_id);
CREATE INDEX idx_col_isbn ON books_collection (isbn);
