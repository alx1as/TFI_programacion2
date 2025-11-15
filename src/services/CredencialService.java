package services;

import java.sql.Connection;
import java.util.List;

import dao.CredencialAccesoDao;
import models.CredencialAcceso;

/**
 *
 * @author Facundo Auciello (Comisión Ag25-2C 07)
 * @author Ayelen Etchegoyen (Comisión Ag25-2C 07)
 * @author Alexia Rubin (Comisión Ag25-2C 05)
 * @author María Victoria Volpe (Comisión Ag25-2C 09)
 */

/**
 * Servicio para gestionar las operaciones relacionadas con las credenciales de
 * acceso.
 */
public class CredencialService implements GenericService<CredencialAcceso> {
    private final CredencialAccesoDao credencialAccesoDao;

    public CredencialService(CredencialAccesoDao credencialAccesoDao) {
        if (credencialAccesoDao == null) {
            throw new IllegalArgumentException("CredencialAccesoDao no puede ser null");
        }
        this.credencialAccesoDao = credencialAccesoDao;
    }

    /**
     * Inserta una nueva credencial de acceso en el sistema.
     * 
     * @param credencialAcceso La credencial de acceso a insertar.
     * @throws Exception Si ocurre un error durante la inserción.
     */
    @Override
    public void insertar(CredencialAcceso credencialAcceso) throws Exception {
        validateCredencialAcceso(credencialAcceso);
        credencialAccesoDao.insertar(credencialAcceso);
    }

    /**
     * Inserta una nueva credencial de acceso en el sistema dentro de una
     * transacción.
     * 
     * @param credencialAcceso La credencial de acceso a insertar.
     * @param conn             Conexión de base de datos para la transacción.
     * @throws Exception Si ocurre un error durante la inserción.
     */
    public void insertarTx(CredencialAcceso credencialAcceso, Connection conn) throws Exception {
        validateCredencialAcceso(credencialAcceso);
        credencialAccesoDao.insertTx(credencialAcceso, conn);
    }

    /**
     * Actualiza una credencial de acceso existente en el sistema.
     * 
     * @param credencialAcceso La credencial de acceso a actualizar.
     * @throws Exception Si ocurre un error durante la actualización.
     */
    @Override
    public void actualizar(CredencialAcceso credencialAcceso) throws Exception {
        validateCredencialAcceso(credencialAcceso);
        if (credencialAcceso.getId() <= 0) {
            throw new IllegalArgumentException("El ID de credencialAcceso debe ser mayor a 0 para actualizar");
        }
        credencialAccesoDao.actualizar(credencialAcceso);
    }

    /**
     * Elimina lógicamente una credencial de acceso (soft delete).
     * Marca la credencial como eliminada=TRUE sin borrarla físicamente.
     *
     * @param id ID de la credencial a eliminar
     * @throws Exception Si id <= 0 o no existe la credencial
     */
    @Override
    public void eliminar(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        credencialAccesoDao.eliminar(id);
    }

    /**
     * Recupera una credencial de acceso.
     * Cambia el campo eliminado a FALSE para reactivar la credencial.
     *
     * @param id ID de la credencial a recuperar
     * @throws Exception Si id <= 0 o no existe la credencial
     */
    @Override
    public void recuperar(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        credencialAccesoDao.recuperar(id);
    }

    /**
     * Obtiene una credencial de acceso por su ID.
     *
     * @param id ID de la credencial a obtener
     * @return La credencial de acceso correspondiente al ID
     * @throws Exception Si id <= 0 o no existe la credencial
     */
    @Override
    public CredencialAcceso getById(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        return credencialAccesoDao.getById(id);
    }

    /**
     * Obtiene todas las credenciales de acceso del sistema.
     *
     * @return Lista de todas las credenciales de acceso
     * @throws Exception Si ocurre un error durante la obtención
     */
    @Override
    public List<CredencialAcceso> getAll() throws Exception {
        return credencialAccesoDao.getAll();
    }

    /**
     * Valida los datos de una credencial de acceso.
     * 
     * @param credencialAcceso La credencial de acceso a validar.
     */

    private void validateCredencialAcceso(CredencialAcceso credencialAcceso) {
        if (credencialAcceso == null) {
            throw new IllegalArgumentException("CredencialAcceso no puede ser null");
        }

        if (credencialAcceso.getIdUsuario() == null) {
            throw new IllegalArgumentException("El ID de usuario no puede estar vacío");
        }

        if (credencialAcceso.getFechaCreacion() == null) {
            throw new IllegalArgumentException("La fecha de creación no puede estar vacía");
        }

        if (credencialAcceso.getContrasenia() == null || credencialAcceso.getContrasenia().trim().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe ser mayor a 8 caracteres");
        }
    }

}
