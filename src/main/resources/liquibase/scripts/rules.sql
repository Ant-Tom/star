-- Создание таблицы правил
CREATE TABLE rules (
    id UUID PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    product_text TEXT NOT NULL
);

CREATE INDEX rules_product_id_index ON rules (product_id);

-- Создание таблицы запросов внутри правил
CREATE TABLE rule_queries (
    rule_id UUID NOT NULL,
    query_type VARCHAR(255) NOT NULL,
    arguments JSON NOT NULL,
    negate BOOLEAN NOT NULL,
    FOREIGN KEY (rule_id) REFERENCES rules (id) ON DELETE CASCADE
);

CREATE INDEX rule_queries_query_type_index ON rule_queries (query_type);

-- Добавление тестовых данных
INSERT INTO rules (id, product_name, product_id, product_text) VALUES
('c3e8a937-7e4b-4cd2-96a6-3b6f0d95b2c9', 'Test Rule', 'test-product-id', 'This is a test rule');

INSERT INTO rule_queries (rule_id, query_type, arguments, negate) VALUES
('c3e8a937-7e4b-4cd2-96a6-3b6f0d95b2c9', 'USER_OF', '["CREDIT"]', true),
('c3e8a937-7e4b-4cd2-96a6-3b6f0d95b2c9', 'TRANSACTION_SUM_COMPARE', '["DEBIT", "DEPOSIT", ">", "100000"]', false);
