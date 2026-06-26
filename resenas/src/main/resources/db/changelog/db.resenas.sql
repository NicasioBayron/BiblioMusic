--liquibase formatted sql

--changeset massi:17 create-reseñas
CREATE TABLE resenas (
    idResena BIGINT AUTO_INCREMENT PRIMARY KEY,
    idProducto BIGINT NOT NULL,
    idUsuario BIGINT NOT NULL,
    calificacion INT NOT NULL,
    comentario TEXT NOT NULL,
    fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CHECK (calificacion >= 1 AND calificacion <= 5)
);

--changeset massi:18 insert-reseñas
INSERT INTO resenas (idProducto, idUsuario, calificacion, comentario) VALUES
(1, 1, 5, 'Excelente producto, muy satisfecho con la compra.'),
(2, 2, 4, 'Buen producto, cumple con lo prometido.'),
(3, 3, 3, 'Producto regular, nada especial.'),
(4, 4, 2, 'No estoy conforme con la calidad del producto.'),
(5, 5, 5, 'Increíble producto, definitivamente lo recomiendo.'),
(1, 6, 4, 'Muy buen producto, aunque el envío fue un poco lento.'),
(2, 7, 3, 'El producto es aceptable, pero esperaba más por el precio.'),
(3, 8, 2, 'No me gustó el producto, no cumplió mis expectativas.'),
(4, 9, 1, 'Producto de mala calidad, no lo recomiendo.'),
(5, 10, 5, 'Excelente producto y servicio al cliente.');