alter sequence product_id_seq restart 10;
insert into product (name, description, kcal)
values ('test product', 'description product 1', 100);
insert into product (name, description, kcal)
values ('test product 1', 'description product 2', 200);