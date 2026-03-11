-- V2__ecommerce_orders.sql
CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        uuid VARCHAR(50) NOT NULL UNIQUE,
                        customer_name VARCHAR(255),
                        customer_email VARCHAR(255),
                        customer_phone VARCHAR(50),
                        total_in_cents BIGINT NOT NULL,
                        currency VARCHAR(10) NOT NULL DEFAULT 'COP',
                        status VARCHAR(30) NOT NULL, -- CREATED | PENDING_PAYMENT | PAID | FAILED | CANCELLED
                        created_at TIMESTAMP DEFAULT now(),
                        updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE order_items (
                             id BIGSERIAL PRIMARY KEY,
                             order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                             producto_id BIGINT, -- link a la tabla productos (nullable si se quiere)
                             nombre VARCHAR(255),
                             cantidad INTEGER NOT NULL,
                             price_in_cents BIGINT NOT NULL,
                             iva_percentage INTEGER,
                             subtotal_in_cents BIGINT NOT NULL
);

CREATE TABLE payments (
                          id BIGSERIAL PRIMARY KEY,
                          order_id BIGINT REFERENCES orders(id) ON DELETE SET NULL,
                          wompi_transaction_id VARCHAR(100),
                          amount_in_cents BIGINT,
                          currency VARCHAR(10),
                          status VARCHAR(30),
                          payment_method_type VARCHAR(50),
                          raw_response TEXT,
                          created_at TIMESTAMP DEFAULT now()
);
