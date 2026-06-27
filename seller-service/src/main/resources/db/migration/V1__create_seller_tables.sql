CREATE TABLE sellers(
    id UUID PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    business_name VARCHAR(150),
    gst_number VARCHAR(50),
    pan_number VARCHAR(20),
    status VARCHAR(30),
    verification_status VARCHAR(30),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE stores(
    id UUID PRIMARY KEY,
    store_name VARCHAR(150),
    description TEXT,
    logo_url VARCHAR(300),
    seller_id UUID UNIQUE REFERENCES sellers(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE warehouses(
    id UUID PRIMARY KEY,
    warehouse_name VARCHAR(120),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    zip_code VARCHAR(20),
    seller_id UUID REFERENCES sellers(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE seller_banks(
    id UUID PRIMARY KEY,
    account_holder_name VARCHAR(120),
    account_number VARCHAR(40),
    ifsc_code VARCHAR(20),
    bank_name VARCHAR(120),
    seller_id UUID UNIQUE REFERENCES sellers(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE seller_documents(
    id UUID PRIMARY KEY,
    document_type VARCHAR(30),
    document_number VARCHAR(100),
    document_url VARCHAR(255),
    verification_status VARCHAR(30),
    seller_id UUID REFERENCES sellers(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_seller_email ON sellers(email);
CREATE INDEX idx_seller_phone ON sellers(phone);
CREATE INDEX idx_seller_name ON sellers(full_name);