
DROP DATABASE IF EXISTS usuariocredencial;
CREATE DATABASE IF NOT EXISTS usuariocredencial;
USE usuariocredencial;

-- Tabla usuarios
CREATE TABLE usuarios(
id_usuario int PRIMARY KEY AUTO_INCREMENT,
nombre varchar(50) NOT NULL,
apellido varchar(50) NOT NULL,
edad TINYINT NOT NULL CHECK (edad >= 18),
email varchar(50) NOT NULL UNIQUE,
usuario varchar(50) NOT NULL UNIQUE,
eliminado BOOLEAN NOT NULL DEFAULT FALSE -- piden en el tfi de prog.
);

-- Tabla credencialAcceso
CREATE TABLE credencialAcceso(
id_credencial int PRIMARY KEY AUTO_INCREMENT,
id_usuario int UNIQUE NOT NULL ,
fecha_creacion DATE NOT NULL, -- cambio fecha de ingreso por fecha de creación
contrasenia varchar(50) NOT NULL,
CONSTRAINT check_longitud_contrasenia
CHECK (LENGTH(contrasenia) > 8),
FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
eliminado BOOLEAN NOT NULL DEFAULT FALSE
);


USE usuariocredencial;

-- INSERTS 
-- Creamos usuarios válidos
INSERT INTO usuarios (nombre, apellido, edad, email, usuario) 
VALUES 
("Susana", "Gimenez", 81, "susanagimenez@gmail.com", "sugimenez"),
("Mirtha", "Legrand", 98, "lachiqui@gmail.com", "lachiqui");

-- Usuario inválido (comentado para pruebas)
-- INSERT INTO usuarios (nombre, apellido, edad, email, usuario) VALUES("Moria", "Casan", 15, "laone@gmail.com", "laone");

-- Credencial válida (asociada al primer usuario creado, que tendría id_usuario = 1 si es la primera en la tabla)
INSERT INTO credencialAcceso (id_usuario, fecha_creacion, contrasenia)
VALUES 
(1, CURDATE(), 'contrasegura123');


SELECT * FROM usuarios;
SELECT * FROM credencialAcceso;

-- Más datos de ejemplo para pruebas

INSERT INTO usuarios (nombre, apellido, edad, email, usuario) VALUES
('Juan', 'Perez', 25, 'juan.perez@example.com', 'jperez'),
('Maria', 'Gomez', 30, 'maria.gomez@example.com', 'mgomez'),
('Carlos', 'Rodriguez', 22, 'carlos.rod@example.com', 'crodriguez'),
('Ana', 'Fernandez', 28, 'ana.fernandez@example.com', 'afernandez'),
('Luis', 'Martinez', 35, 'luis.martinez@example.com', 'lmartinez'),
('Sofia', 'Lopez', 19, 'sofia.lopez@example.com', 'slopez'),
('Miguel', 'Diaz', 45, 'miguel.diaz@example.com', 'mdiaz'),
('Lucia', 'Torres', 27, 'lucia.torres@example.com', 'ltorres'),
('Diego', 'Ruiz', 33, 'diego.ruiz@example.com', 'druiz'),
('Elena', 'Vargas', 29, 'elena.vargas@example.com', 'evargas'),
('Javier', 'Castro', 40, 'javier.castro@example.com', 'jcastro'),
('Paula', 'Romero', 24, 'paula.romero@example.com', 'promero'),
('Andres', 'Herrera', 31, 'andres.herrera@example.com', 'aherrera'),
('Carmen', 'Muñoz', 50, 'carmen.munoz@example.com', 'cmunoz'),
('Roberto', 'Silva', 36, 'roberto.silva@example.com', 'rsilva'),
('Isabel', 'Molina', 26, 'isabel.molina@example.com', 'imolina'), 
('Fernando', 'Ortega', 23, 'fernando.ortega@example.com', 'fortega'),
('Patricia', 'Navarro', 38, 'patricia.navarro@example.com', 'pnavarro'),
('Ricardo', 'Morales', 42, 'ricardo.morales@example.com', 'rmorales'),
('Gabriela', 'Rios', 21, 'gabriela.rios@example.com', 'grios');

INSERT INTO credencialAcceso (id_usuario, fecha_creacion, contrasenia) VALUES
(2, '2023-02-20', 'GomezMaria$99'),
(3, '2023-03-10', 'CarlosRod.2023'),
(4, '2023-04-05', 'AnaFer_Secret!'),
(5, '2023-05-12', 'LuisM_SecurePass'),
(6, '2023-06-18', 'SofiaL_Clave123'),
(7, '2023-07-22', 'MiguelD_987654'),
(8, '2023-08-30', 'LuciaTorres.Tk'),
(9, '2023-09-14', 'DiegoRuiz_Pass'),
(10, '2023-10-01', 'ElenaV_AccesoTotal'),
(11, '2023-11-05', 'JavierC_S3cur3'),
(12, '2023-12-11', 'PaulaR_CodigoX'),
(13, '2024-01-08', 'AndresH_Login24'),
(14, '2024-02-14', 'CarmenM_SuperClave'),
(15, '2024-03-20', 'RobertoS_998877');
