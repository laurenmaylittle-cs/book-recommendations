CREATE TABLE best_reads.user_ratings
(
    id     integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email  text,
    isbn   text,
    rating integer
);
