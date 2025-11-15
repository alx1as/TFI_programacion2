package services;

import java.util.List;

/**
 *
 * @author Facundo Auciello (Comisión Ag25-2C 07)
 * @author Ayelen Etchegoyen (Comisión Ag25-2C 07)
 * @author Alexia Rubin (Comisión Ag25-2C 05)
 * @author María Victoria Volpe (Comisión Ag25-2C 09)
 */

public interface GenericService<T> {
    void insertar(T entidad) throws Exception;

    void actualizar(T entidad) throws Exception;

    void eliminar(int id) throws Exception;

    T getById(int id) throws Exception;

    List<T> getAll() throws Exception;

    void recuperar(int id) throws Exception;
}
