package test;

import dao.CredencialAccesoDao;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import models.CredencialAcceso;

public class TestCredencialAccesoDao {

    public static void main(String[] args) {
        CredencialAccesoDao dao = new CredencialAccesoDao();

        try {
            System.out.println("====== TEST CredencialAccesoDao ======");

            // 1) Crear credencial
            CredencialAcceso c = new CredencialAcceso();
            c.setIdUsuario(1); // pone un id de usuario que exista
            c.setFechaCreacion(Date.from(Instant.now()));
            c.setContrasenia("12345678910");
            c.setEliminado(false);

            dao.crear(c);
            System.out.println("Credencial creada con ID = " + c.getIdCredencial());

            // 2) Leer por ID
            CredencialAcceso leida = dao.leer((long) c.getIdCredencial());
            System.out.println("Leída: ID usuario = " + leida.getIdUsuario());

            // 3) Actualizar
            leida.setContrasenia("abcd123456");
            dao.actualizar(leida);

            CredencialAcceso actualizada = dao.leer((long) leida.getIdCredencial());
            System.out.println("Contraseña actualizada: " + actualizada.getContrasenia());

            // 4) Leer todos
            List<CredencialAcceso> lista = dao.leerTodos();
            System.out.println("Credenciales activas:");
            lista.forEach(cr
                    -> System.out.println("- ID: " + cr.getIdCredencial() + " Usuario: " + cr.getIdUsuario())
            );

            // 5) Eliminar (baja lógica)
            dao.eliminar((long) c.getIdCredencial());
            System.out.println("Credencial eliminada lógicamente.");

            CredencialAcceso eliminada = dao.leer((long) c.getIdCredencial());
            System.out.println("Leer credencial eliminada → " + eliminada);

            // 6) Recuperar
            dao.recuperar((long) c.getIdCredencial());
            System.out.println("Credencial recuperada.");

            CredencialAcceso recuperada = dao.leer((long) c.getIdCredencial());
            System.out.println("Leer recuperada → " + recuperada.getContrasenia());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
