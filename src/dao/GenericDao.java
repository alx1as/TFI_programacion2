
package dao;

import java.util.List;

/**
 *
 * @author Facundo Auciello (Comisión Ag25-2C 07)
 * @author Ayelen Etchegoyen (Comisión Ag25-2C 07)
 * @author Alexia Rubin (Comisión Ag25-2C 05)
 * @author María Victoria Volpe (Comisión Ag25-2C 09)
 */

public interface GenericDao<T> {
    void crear(T entidad) throws Exception;

    T leer(Long id) throws Exception;

    List<T> leerTodos() throws Exception;

    void actualizar(T entidad) throws Exception;

    void eliminar(Long id) throws Exception; // baja lógica

    void recuperar(Long id) throws Exception;
}
