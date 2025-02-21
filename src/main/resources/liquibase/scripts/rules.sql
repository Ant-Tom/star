-- liquibase formatted sql
-- resources/liquibase/scripts/rules.sql

CREATE TABLE rules
(
    id           UUID PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_id   VARCHAR(255) NOT NULL,
    product_text TEXT
);

CREATE INDEX rules_product_id_index ON rules (product_id);

CREATE TABLE rule_queries
(
    rule_id    UUID         NOT NULL,
    query_type VARCHAR(255) NOT NULL,
    arguments  JSON         NOT NULL, -- Используем JSON для хранения списка аргументов
    negate     BOOLEAN      NOT NULL,
    FOREIGN KEY (rule_id) REFERENCES rules (id) ON DELETE CASCADE
);

CREATE INDEX rule_queries_query_type_index ON rule_queries (query_type);