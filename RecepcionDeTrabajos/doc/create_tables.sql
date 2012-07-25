drop table cliente cascade constraints;
drop table pedido cascade constraints;
drop table pedido_item cascade constraints;

CREATE USER faby PASSWORD 'qwerty';

create table cliente (
codigo number(18) PRIMARY KEY,
denominacion varchar2(100),
direccion varchar2(100),
telefono varchar2(100),
email varchar2(100)
);

create table pedido (
numero number(18) PRIMARY KEY,
codigo_cliente number(18) not null 
	CONSTRAINT fk_cliente_pedido REFERENCES cliente
                     (codigo),
fecha date(6)
);

create table pedido_item (
pedido_numero number(18) not null 
	CONSTRAINT fk_pedido_item REFERENCES pedido
                     (numero),
orden number(2) not null,
cantidad number (18), 
detalle varchar2(250), 
observaciones varchar2(250),
CONSTRAINT PK_PEDIDO_ORDEN  PRIMARY KEY(pedido_numero,orden)
);
