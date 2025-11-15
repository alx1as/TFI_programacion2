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

-- Ingreso al sistema
INSERT INTO ingresarSistema (id_credencial, resultado)
VALUES 
(1, 'Exitoso');

SELECT * FROM usuarios;
SELECT * FROM credencialAcceso;
SELECT * FROM ingresarSistema;
