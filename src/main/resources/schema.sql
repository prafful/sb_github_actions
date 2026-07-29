-- Step 02 creates the first application table.
-- IF NOT EXISTS allows the application to restart without recreating the table.

CREATE TABLE IF NOT EXISTS friends
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    city VARCHAR(100) NOT NULL,

    CONSTRAINT pk_friends PRIMARY KEY (id)
);
