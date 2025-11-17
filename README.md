# Sistema de Gestión de Usuarios y Credenciales

Trabajo Final Integrador - Programación II (UTN)

## Descripción

Sistema de gestión de usuarios con credenciales de acceso implementado en Java con MySQL. Utiliza el patrón DAO (Data Access Object) y servicios para la lógica de negocio, implementando operaciones CRUD completas con soft delete.

## Características

- ✅ Gestión completa de usuarios (CRUD)
- ✅ Gestión de credenciales de acceso (relación 1:1 con usuarios)
- ✅ Soft delete (eliminación lógica)
- ✅ Transacciones con rollback automático
- ✅ Validaciones de datos
- ✅ Menú interactivo por consola

## Tecnologías

- **Lenguaje**: Java
- **Base de datos**: MySQL
- **Conector**: MySQL Connector/J
- **IDE**: NetBeans

## Estructura del Proyecto

```
src/
├── config/          # Configuración de conexión a BD y transacciones
├── dao/             # Data Access Objects (acceso a datos)
├── models/          # Entidades del dominio
├── services/        # Lógica de negocio
├── main/            # Punto de entrada y menú de la aplicación
└── sql_scripts/     # Scripts de creación de BD
```

## Requisitos Previos

- Java JDK 8 o superior
- MySQL Server
- MySQL Connector/J (incluido en `lib/`)

## Instalación

1. **Configurar la base de datos**:
Ejecutar los scripts SQL en MySQL en el siguiente orden:
    - Primero: `src/sql_scripts/01_estructura.sql` (crea la base de datos y las tablas)
    - Segundo: `src/sql_scripts/02_datos.sql` (inserta datos de ejemplo)
    - 
2. Agregar el conector MySQL al proyecto:
Descargar MySQL Connector/J (Platform Independent) desde dev.mysql.com e incorporarlo al proyecto:
En NetBeans: Libraries → Add JAR/Folder → seleccionar mysql-connector-j-8.x.x.jar
Verificar que el archivo queda dentro de la carpeta Libraries para habilitar la conexión JDBC.

3. **Configurar credenciales de BD**:
   - Editar `src/config/DatabaseConnection.java`
   - Modificar usuario y contraseña según tu configuración MySQL:
     ```java
     private static final String USUARIO = "root";
     private static final String CONTRASENIA = "tu_password";
     ```

4. **Compilar y ejecutar**:
    - Correr la clase `main.Main`

## Uso

El sistema presenta un menú interactivo con las siguientes opciones:

1. **Gestionar Usuarios**: Crear, listar, actualizar y eliminar usuarios
2. **Gestionar Credenciales**: Crear, listar, actualizar y eliminar credenciales
3. **Buscar Usuario**: Búsqueda por ID o nombre de usuario
0. **Salir**

### Validaciones

- **Usuarios**: 
  - Edad mínima: 18 años
  - Email y nombre de usuario únicos
  
- **Credenciales**:
  - Contraseña: mínimo 9 caracteres
  - Relación 1:1 con usuario

## Patrones de Diseño

- **DAO Pattern**: Separación de la lógica de acceso a datos
- **Service Layer**: Lógica de negocio independiente
- **Generic DAO**: Operaciones CRUD reutilizables
- **Transaction Manager**: Manejo centralizado de transacciones

## Autores

- Facundo Auciello (Comisión Ag25-2C 07)
- Ayelen Etchegoyen (Comisión Ag25-2C 07)
- Alexia Rubin (Comisión Ag25-2C 05)
- María Victoria Volpe (Comisión Ag25-2C 09)
