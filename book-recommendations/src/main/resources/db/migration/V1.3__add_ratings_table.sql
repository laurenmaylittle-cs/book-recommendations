DROP TABLE IF EXISTS user_ratings;
CREATE TABLE user_ratings
(
    id     integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email  text,
    isbn   text,
    rating integer
);
