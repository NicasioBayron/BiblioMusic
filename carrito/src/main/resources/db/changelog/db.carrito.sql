--liquibase formatted sql

--changeset erick:7 create-carrito
create table carrito (
    IdCarrito bigint auto_increment primary key,
    cantidadProducto int not null,
    IdUsuario bigint not null,
    IdProducto bigint not null
);

--changeset erick:8 insert-carrito
insert into carrito (cantidadProducto, IdUsuario, IdProducto) values
(2, 1, 1),
(1, 2, 3),
(4, 3, 2),
(1, 4, 5),
(3, 5, 4),
(2, 6, 6),
(1, 7, 7),
(5, 8, 8),
(2, 9, 9),
(1, 10, 10);