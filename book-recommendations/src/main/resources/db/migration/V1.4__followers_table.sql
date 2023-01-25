DROP TABLE IF EXISTS followers_following;
CREATE TABLE followers_following
(
    id              integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    following_email text,
    follower_email  text
);