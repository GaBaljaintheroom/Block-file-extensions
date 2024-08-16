DROP TABLE IF EXISTS blocked_file_extension CASCADE;

CREATE TABLE blocked_file_extension
(
    is_fixed   BIT         NOT NULL,
    created_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6),
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    updated_at DATETIME(6) NOT NULL,
    name       VARCHAR(20) NOT NULL unique,
    PRIMARY KEY (id)
);