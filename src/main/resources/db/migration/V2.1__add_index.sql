CREATE UNIQUE INDEX idx_u_categories_name ON categories (name);
CREATE UNIQUE INDEX idx_u_products_name ON products (name);
CREATE UNIQUE INDEX idx_u_users_name ON users (name);
CREATE UNIQUE INDEX idx_u_users_email ON users (email);