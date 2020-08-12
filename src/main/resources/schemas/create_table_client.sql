CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE client
(
    user_id   UUID NOT NULL DEFAULT uuid_generate_v1(),
    user_name varchar(100) NOT NULL,
    email    varchar(100) DEFAULT NULL,
    password varchar(10) DEFAULT NULL,
    PRIMARY KEY (user_id)
);
