DROP DATABASE IF EXISTS orders_db;
CREATE DATABASE orders_db;

\c orders_db;

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    external_id VARCHAR(255) NOT NULL,
    total_value DECIMAL(10,2) NOT NULL,
    status VARCHAR(50),
    version INT
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    order_id BIGINT NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);
