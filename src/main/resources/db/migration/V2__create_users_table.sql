CREATE TABLE users
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY,
    version            BIGINT DEFAULT 0,
    creation_date      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by         VARCHAR(100),
    last_modified_by   VARCHAR(100),

    username           VARCHAR(50)  NOT NULL,
    email              VARCHAR(100) NOT NULL,
    password           VARCHAR(255) NOT NULL,
    role               VARCHAR(20)  NOT NULL CHECK (role IN ('EMPLOYEE', 'MANAGER', 'ADMIN')),

    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_email UNIQUE (email)
);