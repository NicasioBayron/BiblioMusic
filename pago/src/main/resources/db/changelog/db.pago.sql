--liquibase formatted sql

--changeset tomy:11 create-pago

create table pago (
    idPago bigint auto_increment primary key,
    idCarrito bigint not null,
    total double not null,
    medioPago varchar(50) not null,
    confirmacionPago varchar(100) not null,
    fechaPago date not null
);

--changeset tomy:12 insert-pago

insert into pago (idCarrito, total, medioPago, confirmacionPago, fechaPago)
values
(1, 25000, 'Debito', 'CONF001', '2026-05-07'),
(2, 15000, 'Credito', 'CONF002', '2026-05-07'),
(3, 32000, 'Transferencia', 'CONF003', '2026-05-07'),
(4, 12000, 'Debito', 'CONF004', '2026-05-07'),
(5, 45000, 'Credito', 'CONF005', '2026-05-07'),
(6, 18000, 'Debito', 'CONF006', '2026-05-07'),
(7, 50000, 'Transferencia', 'CONF007', '2026-05-07'),
(8, 22000, 'Credito', 'CONF008', '2026-05-07'),
(9, 9000, 'Debito', 'CONF009', '2026-05-07'),
(10, 27000, 'Credito', 'CONF010', '2026-05-07');