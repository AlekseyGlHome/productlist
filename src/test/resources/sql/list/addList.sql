alter sequence list_id_seq restart 10;
insert into list (name) values ('test list');
insert into list (name) values ('test list 1');

alter sequence product_id_seq restart 10;
insert into product (name, description, kcal) values ('test product', 'description product 1', 100);
insert into product (name, description, kcal) values ('test product 1', 'description product 2', 200);


insert into list_products (list_id, products_id) values (10,10);

insert into list_products (list_id, products_id) values (10,11);

insert into list_products (list_id, products_id) values (11,10);