-- liquibase formatted sql

-- changeset kkatyshev:1

CREATE TABLE transactions (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255),
    product_id VARCHAR(255),
    type VARCHAR(50),
    amount DOUBLE
);

CREATE INDEX transactions_user_id_index ON transactions (user_id);
CREATE INDEX transactions_type_index ON transactions (user_id, type);
CREATE UNIQUE INDEX unique_user_product_index ON transactions (user_id, product_id);
