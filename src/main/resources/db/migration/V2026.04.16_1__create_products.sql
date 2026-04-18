CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type_product VARCHAR(255) NOT NULL,
    account VARCHAR(20) NOT NULL,
    balance BIGINT DEFAULT 0,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);