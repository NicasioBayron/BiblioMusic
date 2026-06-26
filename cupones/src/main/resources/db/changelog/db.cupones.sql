--liquibase formatted sql

--changeset ella:15 create-cupones
CREATE TABLE cupones (
    idCupon BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    porcentajeDescuento INT NOT NULL,
    fechaExpiracion DATE NOT NULL,
    activo BOOLEAN NOT NULL
);

--changeset ella:16 insert-cupones
INSERT INTO cupones (codigo, porcentajeDescuento, fechaExpiracion, activo) VALUES
('BIBLIO10', 10, '2026-12-31', TRUE),
('MUSIC20', 20, '2026-10-30', TRUE),
('KENDRICK30', 30, '2026-08-15', TRUE),
('YE2026', 15, '2026-05-01', FALSE),
('LECTURA5', 5, '2026-11-20', TRUE),
('ARTES25', 25, '2026-07-31', TRUE),
('CINE15', 15, '2026-04-01', FALSE),
('DEPORTES20', 20, '2026-10-10', TRUE),
('GAMING30', 30, '2026-09-20', TRUE),
('MODA10', 10, '2026-11-30', TRUE);

