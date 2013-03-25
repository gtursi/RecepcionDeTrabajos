--AGREGAR AQUI NUEVA VERSIONES! ACTUALIZAR PROPERTY DB_VERSION
-- Version 20130319
alter table pedido_item add costo number(18,2);
alter table pedido_item add precio number(18,2);
update property set value='20130319' where name='db_version';

--version 3
alter table pedido_item add comentarios varchar2(255);
alter table pedido add entregado boolean DEFAULT false not null;
update pedido set entregado = true;
update property set value='3' where name='db_version';
