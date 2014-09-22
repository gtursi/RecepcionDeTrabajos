--AGREGAR AQUI NUEVA VERSIONES! ACTUALIZAR PROPERTY DB_VERSION
-- Version 20130319
alter table pedido_item add costo number(18,2);
alter table pedido_item add precio number(18,2);
update property set value='20130319' where name='db_version';

--Ver DatabaseUpdater.java