CREATE TABLE event (
    event_id UUID NOT NULL DEFAULT uuid_generate_v1(),
    event_name varchar(50) NOT NULL,
    date date NOT NULL DEFAULT now(),
    time time without time zone NOT NULL DEFAULT now(),
    completed bool NOT NULL DEFAULT false,
    user_id uuid NOT NULL,
    PRIMARY KEY (event_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES client (user_id)
);