CREATE TABLE active_channel
(
    id           SERIAL PRIMARY KEY,
    channel      VARCHAR NOT NULL,
    time_of_join VARCHAR NOT NULL
);
