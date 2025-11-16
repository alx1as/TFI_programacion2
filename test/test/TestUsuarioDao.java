
package test;

import dao.UsuarioDao;
import dao.UsuarioDao;
import models.Usuario;
import java.util.List;

public class TestUsuarioDao {

    public static void main(String[] args) {

        UsuarioDao dao = new UsuarioDao();

        try {
            System.out.println("===== TEST UsuarioDao =====");

            // 1) CREAR USUARIO
            Usuario u = new Usuario();
            u.setNombre("Mavi");
            u.setApellido("Volpe");
            u.setEdad(33);
            u.setEmail("mavi@test.com");
            u.setUsuario("maviv");

            dao.crear(u);
            System.out.println("Usuario creado con ID = " + u.getIdUsuario());

            // 2) LEER POR ID
            Usuario leido = dao.leer((long) u.getIdUsuario());
            System.out.println("Leído: " + leido.getNombre() + " " + leido.getApellido());

            // 3) ACTUALIZAR
            leido.setNombre("Victoria");
            dao.actualizar(leido);

            Usuario actualizado = dao.leer((long) leido.getIdUsuario());
            System.out.println("Nombre actualizado: " + actualizado.getNombre());

            // 4) LEER TODOS
            List<Usuario> lista = dao.leerTodos();
            System.out.println("Usuarios activos:");
            lista.forEach(us -> System.out.println("- " + us.getIdUsuario() + " " + us.getNombre()));

            // 5) BUSCAR POR USUARIO
            Usuario porUsuario = dao.buscarPorUsuario("maviv");
            System.out.println("Buscar por usuario → " + (porUsuario != null ? porUsuario.getNombre() : "No encontrado"));

            // 6) BUSCAR POR EMAIL
            Usuario porEmail = dao.buscarPorEmail("mavi@test.com");
            System.out.println("Buscar por email → " + (porEmail != null ? porEmail.getNombre() : "No encontrado"));

            // 7) ELIMINAR (baja lógica)
            dao.eliminar((long) u.getIdUsuario());
            System.out.println("Usuario eliminado lógicamente");

            Usuario eliminado = dao.leer((long) u.getIdUsuario());
            System.out.println("Leer usuario eliminado → " + eliminado);

            // 8) RECUPERAR
            dao.recuperar((long) u.getIdUsuario());
            System.out.println("Usuario recuperado");

            Usuario recuperado = dao.leer((long) u.getIdUsuario());
            System.out.println("Leer usuario recuperado → " + recuperado.getNombre());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
