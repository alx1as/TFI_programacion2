

package dao;


import java.util.List;

public interface GenericDao<T> {
    void crear(T entidad) throws Exception;
    T leer(Long id) throws Exception;
    List<T> leerTodos() throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(Long id) throws Exception; // baja lógica
    void recuperar(Long id) throws Exception; 
}
