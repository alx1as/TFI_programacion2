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



