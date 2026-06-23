CREATE TABLE customers
(
    id UUID PRIMARY KEY,

    user_id UUID NOT NULL UNIQUE,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100),

    email VARCHAR(255) NOT NULL,

    phone VARCHAR(20),

    loyalty_points INTEGER NOT NULL,

    status VARCHAR(30) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP
);

CREATE TABLE customer_addresses
(
    id UUID PRIMARY KEY,

    customer_id UUID NOT NULL,

    address_type VARCHAR(30),

    address_line1 VARCHAR(255) NOT NULL,

    address_line2 VARCHAR(255),

    city VARCHAR(100) NOT NULL,

    state VARCHAR(100) NOT NULL,

    country VARCHAR(100) NOT NULL,

    postal_code VARCHAR(20) NOT NULL,

    is_default BOOLEAN NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP,

    CONSTRAINT fk_customer_address
        FOREIGN KEY(customer_id)
        REFERENCES customers(id)
);