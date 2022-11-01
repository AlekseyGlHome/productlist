create table if not exists public.product (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	description varchar(255) NULL,
	"name" varchar(255) NOT NULL,
	kcal int,
	CONSTRAINT product_pkey PRIMARY KEY (id)
);

create table if not exists public.list (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"name" varchar(255) NOT NULL,
	 CONSTRAINT list_pkey PRIMARY KEY (id)
);

create table if not exists public.list_products (
	list_id int8 NOT NULL,
	products_id int8 NOT NULL,
	CONSTRAINT list_products_pk UNIQUE (list_id, products_id),
	CONSTRAINT fk_product FOREIGN KEY (products_id) REFERENCES public.product(id),
	CONSTRAINT fk_list FOREIGN KEY (list_id) REFERENCES public.list(id)
);