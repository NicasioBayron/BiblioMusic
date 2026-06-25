--liquibase formatted sql

--changeset bayron:1
create table usuario (
    id bigint auto_increment primary key,
    nombre varchar(255) not null,
    apellido varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    rol varchar(50) not null
);

--changeset bayron:2
insert into usuario (nombre, apellido, email, password, rol) values
('Bayron', 'García', 'bayron.garcia@example.com', 'contraseña123', 'cliente'),
('María', 'López', 'maria.lopez@example.com', 'contraseña456', 'cliente'),
('Eileen', 'Smith', 'eileen.smith@example.com', 'contraseña789', 'cliente'),
('Luis', 'Rodríguez', 'luis.rodriguez@example.com', 'contraseña012', 'cliente'),
('Ana', 'Martínez', 'ana.martinez@example.com', 'contraseña345', 'cliente'),
('Carlos', 'González', 'carlos.gonzalez@example.com', 'contraseña678', 'cliente'),
('Sofía', 'Hernández', 'sofia.hernandez@example.com', 'contraseña901', 'cliente'),
('José', 'Pérez', 'jose.perez@example.com', 'contraseña234', 'cliente'),
('Laura', 'Gómez', 'laura.gomez@example.com', 'contraseña567', 'cliente'),
('Admin', 'User', 'admin.user@example.com', 'admin123', 'admin');

--changeset bayron:3
-- Encriptar todas las contraseñas que estaban en texto plano a SHA-1
UPDATE usuario SET password = SHA1(password);