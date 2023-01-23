DROP TABLE IF EXISTS best_reads.followers_following;
CREATE TABLE best_reads.followers_following
(
    id              integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    following_email text,
    follower_email  text
);