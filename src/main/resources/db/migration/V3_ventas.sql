CREATE TABLE ventas (
                        id BIGSERIAL PRIMARY KEY,
                        uuid VARCHAR(50) NOT NULL UNIQUE,

                        cliente_nombre VARCHAR(255),
                        cliente_tipo_documento_id INT,
                        cliente_documento VARCHAR(50),
                        cliente_org_juridica_id INT,
                        cliente_tributo_id INT,
                        cliente_email VARCHAR(255),
                        cliente_telefono VARCHAR(50),

                        payment_form_id INT,
                        payment_method_id INT,
                        seller_id INT,

                        total NUMERIC(14,2) NOT NULL,
                        currency VARCHAR(10) NOT NULL DEFAULT 'COP',
                        estado VARCHAR(30) NOT NULL,

                        created_at TIMESTAMP DEFAULT now(),
                        updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE venta_items (
                             id BIGSERIAL PRIMARY KEY,
                             venta_id BIGINT NOT NULL REFERENCES ventas(id) ON DELETE CASCADE,
                             producto_id BIGINT,
                             nombre VARCHAR(255),
                             cantidad INTEGER NOT NULL,
                             precio_unitario NUMERIC(14,2) NOT NULL,
                             iva_percentage INTEGER,
                             subtotal NUMERIC(14,2) NOT NULL
);

CREATE TABLE invoice_records (
                                 id BIGSERIAL PRIMARY KEY,
                                 venta_id BIGINT REFERENCES ventas(id) ON DELETE SET NULL,
                                 factus_bill_id VARCHAR(200),
                                 number VARCHAR(100),
                                 status VARCHAR(50),
                                 raw_response TEXT,
                                 created_at TIMESTAMP DEFAULT now()
);
