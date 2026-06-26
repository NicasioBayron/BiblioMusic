--liquibase formatted sql

--changeset alvarita:12 create-envio

create table envio (
    id_envio bigint auto_increment primary key,
    id_pago bigint not null,
    id_usuario bigint not null,
    id_carrito bigint not null,
    estado_envio varchar(255) not null,
    fecha_envio date not null,
    direccion_envio varchar(255) not null
);

--changeset alvarita:13 insert-envio

insert into envio
(id_pago, id_usuario, id_carrito, estado_envio, fecha_envio, direccion_envio)
values
(1, 1, 1, 'Pendiente', '2026-07-07', 'Santiago Centro'),
(2, 2, 2, 'En camino', '2026-08-07', 'San Miguel'),
(3, 3, 3, 'Entregado', '2026-03-07', 'Providencia'),
(4, 4, 4, 'Pendiente', '2026-05-07', 'La Florida'),
(5, 5, 5, 'En camino', '2026-01-07', 'Maipu'),
(6, 6, 6, 'Entregado', '2026-09-07', 'Ñuñoa'),
(7, 7, 7, 'Pendiente', '2026-04-07', 'Las Condes'),
(8, 8, 8, 'En camino', '2026-02-07', 'Puente Alto'),
(9, 9, 9, 'Entregado', '2026-05-07', 'Recoleta'),
(10, 10, 10, 'Pendiente', '2026-09-07', 'Independencia');