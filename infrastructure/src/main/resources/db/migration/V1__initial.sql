CREATE TABLE category (
 id VARCHAR(36) NOT NULL primary key,
 name VARCHAR(255) NOT NULL,
 description VARCHAR(4000),
 active BOOLEAN NOT NULL,
 created_at DATETIME(6) NOT NULL,
 updated_at DATETIME(6),
 deleted_at DATETIME(6)
);