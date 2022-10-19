CREATE SCHEMA IF NOT EXISTS best_reads;

DROP TABLE IF EXISTS test_book;

DROP TABLE IF EXISTS best_reads.test_book;

CREATE TABLE best_reads.test_book (
  id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  title text
);
