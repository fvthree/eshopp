create extension if not exists "uuid-ossp";

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
-- DROP TABLE IF EXISTS roles;

create sequence orders_item_sequence start 1 increment 1;
create sequence orders_sequence start 1 increment 1;
create sequence product_sequence start 1 increment 1;
create sequence category_sequence start 1 increment 1;
create sequence user_sequence start 1 increment 1;
-- create sequence role_sequence start 1 increment 1;

CREATE TABLE categories(
	category_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
	name varchar(100) not null,
	color varchar(100) not null,
	icon text,
	image text,
	date_created timestamp without time zone,
	last_updated timestamp without time zone
);

CREATE TABLE products(
	product_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
	name varchar(100) not null,
	description varchar(200) not null,
	rich_description text,
	image text,
	brand varchar(100),
	price double precision,
	category_id uuid constraint product_category_id_fk references categories,
	count_in_stock int default 1,
	rating double precision,
	num_reviews int default 1,
	is_featured  boolean,
	date_created timestamp without time zone,
	last_updated timestamp without time zone
);

CREATE TABLE roles(
	role_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
	name varchar(255) not null,
	created_at timestamp without time zone,
	updated_at timestamp without time zone
);


CREATE TABLE users(
	user_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
	name varchar(100) not null,
	email varchar(100) not null,
	password varchar(200) not null,
	is_active boolean,
	street text,
	apartment text,
	city varchar(100) not null,
	zip varchar(100) not null,
	country varchar(100) not null,
	phone varchar(100) not null,
	is_admin boolean,
	is_not_locked boolean,
	is_verified boolean,
	token uuid DEFAULT uuid_generate_v4(),
	date_created timestamp without time zone,
	last_updated timestamp without time zone
);

CREATE TABLE user_roles(
 	user_roles_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
	user_id uuid constraint user_roles_user_id_fk references users,
	role_id uuid constraint user_roles_role_id_fk references roles
);

CREATE TABLE orders(
	order_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
	shipping_address_1 text not null,
	shipping_address_2 text not null,
	city varchar(100) not null,
	zip varchar(100) not null,
	country varchar(100) not null,
	phone varchar(100) not null,
	status varchar(100) not null,
	total_price bigint,
	user_id uuid CONSTRAINT order_id_user_fk references users
);
CREATE TABLE order_items(
	order_item_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
	product_id uuid CONSTRAINT order_item_id_product_fk references products,
	quantity int not null
);

