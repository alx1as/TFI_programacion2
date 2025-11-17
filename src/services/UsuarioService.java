package services;

import java.sql.Connection;
import java.util.List;
import java.util.regex.Pattern;

import config.DatabaseConnection;
import config.TransactionManager;
import dao.UsuarioDao;
import models.CredencialAcceso;
import models.Usuario;

/**
 *
 * @author Facundo Auciello (Comisión Ag25-2C 07)
 * @author Ayelen Etchegoyen (Comisión Ag25-2C 07)
 * @author Alexia Rubin (Comisión Ag25-2C 05)
 * @author María Victoria Volpe (Comisión Ag25-2C 09)
 */

/**
 * Implementación del servicio de negocio para la entidad Usuario.
 * Capa intermedia entre la UI y el DAO que aplica validaciones de negocio
 * complejas.
 *
 * Responsabilidades:
 * - Validar datos de usuario ANTES de persistir
 * - Garantizar unicidad del email y usuario en el sistema
 * - COORDINAR operaciones entre Usuario y CredencialAcceso (transaccionales)
 * - Proporcionar métodos de búsqueda especializados (por email, usuario,
 * nombre/apellido)
 *
 * Patrón: Service Layer con inyección de dependencias y coordinación de
 * servicios
 */
public class UsuarioService implements GenericService<Usuario> {
    private final UsuarioDao usuarioDao;
    private final CredencialService credencialService;

    // Patrón para validación de email
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Patrón para validación de nombres (solo letras y espacios)
    private static final Pattern NOMBRE_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");

    // Patrón para validación de username (letras, números y guión bajo)
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

    public UsuarioService(UsuarioDao usuarioDao, CredencialService credencialService) {
        if (usuarioDao == null) {
            throw new IllegalArgumentException("UsuarioDAO no puede ser null");
        }
        if (credencialService == null) {
            throw new IllegalArgumentException("credencialService no puede ser null");
        }
        this.usuarioDao = usuarioDao;
        this.credencialService = credencialService;
    }

    /**
     * Inserta un nuevo usuario tras validar sus datos y unicidad de email y
     * usuario.
     * 
     * @param usuario Usuario a insertar
     * @throws Exception Si la validación falla o hay error de BD
     */
    @Override
    public void insertar(Usuario usuario) throws Exception {
        validateUsuario(usuario);
        validateUsuarioUnique(usuario.getUsuario(), usuario.getIdUsuario());
        validateEmailUnique(usuario.getEmail(), usuario.getIdUsuario());

        usuarioDao.crear(usuario);
    }

    /**
     * Inserta un nuevo usuario junto con su credencial de acceso en una
     * transacción.
     * 
     * @param usuario Usuario a insertar
     * @throws Exception Si la validación falla o hay error de BD
     */
    public void insertarTx(Usuario usuario) throws Exception {
        validateUsuario(usuario);
        validateUsuarioUnique(usuario.getUsuario(), usuario.getIdUsuario());
        validateEmailUnique(usuario.getEmail(), usuario.getIdUsuario());

        Connection conn = DatabaseConnection.conectar();
        if (conn == null) {
            throw new Exception("No se pudo establecer conexión con la base de datos");
        }

        try (TransactionManager txManager = new TransactionManager(conn)) {

            txManager.startTransaction();
            usuarioDao.crear(usuario, conn);
            if (usuario.getCredencial() != null) {
                if (usuario.getCredencial().getId() == 0) {
                    // obtener id_usuario recien insertado
                    usuario.getCredencial().setIdUsuario(usuario.getIdUsuario());

                    // CredencialAcceso nuevo: insertar primero para obtener ID autogenerado
                    CredencialAcceso credencial = usuario.getCredencial();
                    credencialService.insertarTx(credencial, conn);

                } else {
                    credencialService.actualizar(usuario.getCredencial());
                }
            }
            txManager.commit();

        } catch (Exception e) {
            // En caso de excepción, se hace rollback automáticamente en close()
            throw new Exception("Error al crear el usuario: " + e.getMessage());
        }
    }

    /**
     * Actualiza un usuario tras validar sus datos y unicidad de email y usuario.
     * 
     * @param usuario Usuario a actualizar
     * @throws Exception Si la validación falla o hay error de BD
     */
    @Override
    public void actualizar(Usuario usuario) throws Exception {
        validateUsuario(usuario);
        if (usuario.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("El ID de la usuario debe ser mayor a 0 para actualizar");
        }
        validateEmailUnique(usuario.getEmail(), usuario.getIdUsuario());
        validateUsuarioUnique(usuario.getUsuario(), usuario.getIdUsuario());
        usuarioDao.actualizar(usuario);
    }

    /**
     * Elimina lógicamente una usuario (soft delete).
     * Marca el usuario como eliminado=TRUE sin borrarla físicamente.
     *
     * @param id ID del usuario a eliminar
     * @throws Exception Si id <= 0 o no existe la usuario
     */
    @Override
    public void eliminar(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        usuarioDao.eliminar((long) id);
    }

    /**
     * Recupera lógicamente una usuario (undo soft delete).
     * Marca al usuario como eliminado=FALSE.
     *
     * @param id ID de la usuario a recuperar
     * @throws Exception Si id <= 0 o no existe la usuario
     */
    @Override
    public void recuperar(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        usuarioDao.recuperar((long) id);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario a buscar
     * @return Usuario encontrado, o null si no existe o está eliminado
     * @throws Exception Si id <= 0 o hay error de BD
     */
    @Override
    public Usuario getById(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        return usuarioDao.leer((long) id);
    }

    /**
     * Obtiene todos los usuarios activos (eliminado=FALSE).
     *
     * @return Lista de usuarios activos
     * @throws Exception Si hay error de BD
     */
    @Override
    public List<Usuario> getAll() throws Exception {
        return usuarioDao.leerTodos();
    }

    /**
     * Expone el servicio de credenciales asociado.
     * Necesario para operaciones de menú que trabajan directamente con
     * credenciales.
     *
     * @return Instancia de CredencialService inyectada en este servicio
     */
    public CredencialService getCredencialService() {
        return this.credencialService;
    }

    /**
     * Busca usuarios por nombre o apellido (búsqueda flexible con LIKE).
     * Usa UsuarioDAO.buscarPorNombreApellido() que realiza:
     * - LIKE %filtro% en nombre O apellido
     * - Insensible a mayúsculas/minúsculas (LOWER())
     * - Solo usuarios activos (eliminado=FALSE)
     *
     * @param filtro Texto a buscar (no puede estar vacío)
     * @return Lista de usuarios que coinciden con el filtro (puede estar vacía)
     * @throws IllegalArgumentException Si el filtro está vacío
     * @throws Exception                Si hay error de BD
     */
    public List<Usuario> buscarPorNombreApellido(String filtro) throws Exception {
        if (filtro == null || filtro.trim().isEmpty()) {
            throw new IllegalArgumentException("El filtro de búsqueda no puede estar vacío");
        }
        return usuarioDao.buscarPorNombreApellido(filtro);
    }

    /**
     * Busca un usuario por nombre de usuario.
     * Usa UsuarioDao.buscarPorUsuario() que realiza búsqueda exacta (=).
     *
     * Uso típico:
     * - Validar unicidad del usuario (validateDniUnique)
     * - Buscar usuario específica desde el menú
     *
     * @param campoUsuario Nombre de usuario exacto a buscar (no puede estar vacío)
     * @return Usuario con ese nombre de usuario, o null si no existe o está
     *         eliminado
     * @throws IllegalArgumentException Si el nombre de usuario está vacío
     * @throws Exception                Si hay error de BD
     */
    public Usuario buscarPorUsuario(String campoUsuario) throws Exception {
        if (campoUsuario == null || campoUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
        return usuarioDao.buscarPorUsuario(campoUsuario);
    }

    /**
     * Busca un usuario por email.
     * Usa UsuarioDao.buscarPorEmail() que realiza búsqueda exacta (=).
     *
     * @param email Email exacto a buscar (no puede estar vacío)
     * @return Usuario encontrado, o null si no existe o está eliminado
     * @throws IllegalArgumentException Si el email está vacío
     * @throws Exception                Si hay error de BD
     */
    public Usuario buscarPorEmail(String email) throws Exception {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        return usuarioDao.buscarPorEmail(email);
    }

    /**
     * Valida que un nombre de usuario sea único en el sistema.
     * 
     * @param usuario
     * @param usuarioId
     * @throws Exception
     */
    private void validateUsuarioUnique(String usuario, Integer usuarioId) throws Exception {
        Usuario existente = usuarioDao.buscarPorUsuario(usuario);
        if (existente != null) {
            if (usuarioId == null || existente.getIdUsuario() != usuarioId) {
                throw new IllegalArgumentException("Ya existe un usuario con el nombre de usuario: " + usuario);
            }
        }
    }

    /**
     * Elimina la credencial de acceso asociada a un usuario.
     * Realiza la secuencia transaccional:
     * 1) Actualiza el usuario para desvincular la credencial (FK a NULL)
     * 2) Elimina la credencial de acceso
     * 
     * @param usuarioId          ID del usuario
     * @param credencialAccesoId ID de la credencial de acceso a eliminar
     * @throws Exception Si hay error de validación o de BD
     */
    public void eliminarCredencialAccesoDeUsuario(int usuarioId, int credencialAccesoId) throws Exception {
        if (usuarioId <= 0 || credencialAccesoId <= 0) {
            throw new IllegalArgumentException("Los IDs deben ser mayores a 0");
        }

        Usuario usuario = usuarioDao.leer((long) usuarioId);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId);
        }

        if (usuario.getCredencial() == null || usuario.getCredencial().getId() != credencialAccesoId) {
            throw new IllegalArgumentException("La credencial no pertenece a este usuario");
        }

        // Secuencia transaccional: actualizar FK → eliminar domicilio
        usuario.setCredencial(null);
        usuarioDao.actualizar(usuario);
        credencialService.eliminar(credencialAccesoId);
    }

    /**
     * Valida que un usuario tenga datos correctos.
     *
     * Reglas de negocio aplicadas:
     * - Nombre, apellido, email, usuario y edad son obligatorios
     * - Se verifica trim() para evitar strings solo con espacios
     * - Email debe tener formato válido
     * - Nombre y apellido solo pueden contener letras
     * - Username debe tener entre 4 y 30 caracteres
     * - Edad debe estar entre 18 y 100 años
     *
     * @param usuario Usuario a validar
     * @throws IllegalArgumentException Si alguna validación falla
     */
    private void validateUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }

        // Validación de nombre
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (usuario.getNombre().length() < 2 || usuario.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre debe tener entre 2 y 50 caracteres");
        }
        if (!NOMBRE_PATTERN.matcher(usuario.getNombre()).matches()) {
            throw new IllegalArgumentException("El nombre solo puede contener letras");
        }

        // Validación de apellido
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (usuario.getApellido().length() < 2 || usuario.getApellido().length() > 50) {
            throw new IllegalArgumentException("El apellido debe tener entre 2 y 50 caracteres");
        }
        if (!NOMBRE_PATTERN.matcher(usuario.getApellido()).matches()) {
            throw new IllegalArgumentException("El apellido solo puede contener letras");
        }

        // Validación de edad
        if (usuario.getEdad() == null || usuario.getEdad() < 18) {
            throw new IllegalArgumentException("El usuario debe tener por lo menos 18 años");
        }
        if (usuario.getEdad() > 100) {
            throw new IllegalArgumentException("La edad debe ser menor a 100 años");
        }

        // Validación de email
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (!EMAIL_PATTERN.matcher(usuario.getEmail()).matches()) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }

        // Validación de username
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
        if (usuario.getUsuario().length() < 4 || usuario.getUsuario().length() > 30) {
            throw new IllegalArgumentException("El nombre de usuario debe tener entre 4 y 30 caracteres");
        }
        if (!USERNAME_PATTERN.matcher(usuario.getUsuario()).matches()) {
            throw new IllegalArgumentException("El nombre de usuario solo puede contener letras, números y guión bajo");
        }
    }

    /**
     * Valida que un email sea único en el sistema.
     * 
     * @param email
     * @param usuarioId
     * @throws Exception
     */
    private void validateEmailUnique(String email, Integer usuarioId) throws Exception {
        Usuario existente = usuarioDao.buscarPorEmail(email);
        if (existente != null) {
            if (usuarioId == null || existente.getIdUsuario() != usuarioId) {
                throw new IllegalArgumentException("Ya existe un usuario con el email: " + email);
            }
        }
    }

}
