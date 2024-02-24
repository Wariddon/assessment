DROP TABLE IF EXISTS lottery CASCADE;

CREATE TABLE lottery
(
    id          SERIAL PRIMARY KEY,
    ticket VARCHAR(255) UNIQUE NOT NULL,
    price      INTEGER  NOT NULL,
	amount      INTEGER  NOT NULL
);


DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE TABLE user_ticket
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    ticket_id INTEGER UNIQUE NOT NULL
);
