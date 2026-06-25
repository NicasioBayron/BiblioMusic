--liquibase formatted sql

--changeset eileen:3
create table producto (
    id_producto bigint auto_increment primary key,
    nombre_producto varchar(255) not null,
    tipo_producto varchar(255) not null,
    precio int not null,
    stock int not null
);
--changeset eileen:4
create table detalle_producto (
    id_detalle bigint auto_increment primary key,
    autor varchar(255) not null,
    genero varchar(255) not null,
    descripcion varchar(255) not null,
    id_producto bigint not null,
    foreign key (id_producto) references producto(id_producto)
);

--changeset eileen:5
insert into producto (nombre_producto, tipo_producto, precio, stock) values
('My Beatiful Dark Twisted Fantasy', 'Album', 20000, 100),
('To Pimp a Butterfly', 'Album', 15000, 50),
('Dawn FM', 'Album', 18000, 75),
('Graduation', 'Album', 22000, 120),
('Mr Morale & The Big Steppers', 'Album', 20000, 80),
('Orgullo y Perjuicio', 'Libro', 5000, 200),
('Cien Años de Soledad', 'Libro', 7000, 150),
('El Gran Gatsby', 'Libro', 6000, 180),
('La Otra Hermana Bennet', 'Libro', 5500, 220),
('Cumbres Borrascosas', 'Libro', 6500, 170);

--changeset eileen:6 
insert into detalle_producto (autor, genero, descripcion, id_producto) values
('Kanye West', 'Hip-Hop', 'Una obra maestra maximalista sobre la fama y el exceso. Épica sonora que fusiona rap y rock en el álbum más ambicioso del siglo XXI.', 1),
('Kendrick Lamar', 'Hip-Hop', 'Un manifiesto revolucionario de jazz y hip-hop. Poesía cruda que disecciona la identidad, la lucha social y racial y la redención con una profundidad intelectual inigualable.', 2),
('The Weeknd', 'R&B', 'Una experiencia psicodélica y ochentera en el "purgatorio". Sintetizadores brillantes y ritmos bailables que exploran el arrepentimiento y la búsqueda de la luz espiritual.', 3),
('Kanye West', 'Hip-Hop', 'Un hito en la evolución del rap. Con producción innovadora y colaboraciones estelares, este álbum marcó un cambio hacia un sonido más electrónico y accesible, consolidando a Kanye como un visionario musical.', 4),
('Kendrick Lamar', 'Hip-Hop', 'Una catarsis honesta y transformadora. Con letras profundas y producción ecléctica, este álbum aborda temas de trauma, redención y la complejidad de la experiencia humana con una honestidad brutal. La terapia hecha álbum.', 5),
('Orgullo y Perjuicio', 'Novela', 'Una novela clásica de Jane Austen que explora las complejidades de las relaciones humanas, el amor y la sociedad en la Inglaterra del siglo XIX.', 6),
('Cien Años de Soledad', 'Novela', 'Una obra maestra de Gabriel García Márquez que narra la historia de la familia Buendía a lo largo de varias generaciones en el pueblo ficticio de Macondo, fusionando realismo mágico con la historia latinoamericana.', 7),
('El Gran Gatsby', 'Novela', 'Una novela icónica de F. Scott Fitzgerald que captura la decadencia y el sueño americano en la década de 1920 a través de la historia del misterioso millonario Jay Gatsby y su amor por Daisy Buchanan.', 8),
('La Otra Hermana Bennet', 'Novela', 'Una novela de Jane Austen que explora las complejidades de las relaciones humanas y el amor en la Inglaterra del siglo XIX.', 9),
('Cumbres Borrascosas', 'Novela', 'Una novela gótica de Emily Brontë que narra la apasionada y tormentosa relación entre Heathcliff y Catherine Earnshaw, explorando temas de amor, venganza y redención en los páramos ingleses.', 10);