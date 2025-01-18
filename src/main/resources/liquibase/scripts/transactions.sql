-- liquibase formatted sql

-- changeset kkatyshev:1

CREATE TABLE users (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(15),
    date_of_birth DATE
);

CREATE INDEX users_email_index ON users (email);
CREATE INDEX users_phone_index ON users (phone);

-- changeset kkatyshev:2

CREATE TABLE products (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100) NOT NULL,
    description TEXT,
    rules TEXT
);

CREATE INDEX products_name_index ON products (name);
CREATE INDEX products_type_index ON products (type);

-- changeset kkatyshev:3

CREATE TABLE user_transactions (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    amount DOUBLE,
    type VARCHAR(50)
);

CREATE INDEX transactions_user_id_index ON user_transactions (user_id);
CREATE INDEX transactions_product_id_index ON user_transactions (product_id);
CREATE INDEX transactions_date_index ON user_transactions (transaction_date);
CREATE INDEX transactions_type_index ON user_transactions (type);
