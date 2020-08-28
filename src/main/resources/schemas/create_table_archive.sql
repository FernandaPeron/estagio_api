CREATE TABLE archive (
     archive_id UUID NOT NULL DEFAULT uuid_generate_v1(),
     archive_name varchar(100) NOT NULL,
     user_id UUID NOT NULL,
     file BIGINT NOT NULL,
     PRIMARY KEY(archive_id),
     CONSTRAINT fk_user
         FOREIGN KEY(user_id)
             REFERENCES client(user_id)
);

