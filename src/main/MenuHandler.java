package main;

import models.Usuario;
import models.CredencialAcceso;
import services.UsuarioService;
import services.CredencialService;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author Facundo Auciello (Comisión Ag25-2C 07)
 * @author Ayelen Etchegoyen (Comisión Ag25-2C 07)
 * @author Alexia Rubin (Comisión Ag25-2C 05)
 * @author María Victoria Volpe (Comisión Ag25-2C 09)
 */

public class MenuHandler {

    // el handler que ejecuta las operaciones del menu y contiene toda la logica de
    // interaccion con el usuario

    private final Scanner scanner;
    private final UsuarioService usuarioService;
    private final CredencialService credencialService;

    // constructor que recibe los services ya inicializados

    public MenuHandler(Scanner scanner, UsuarioService usuarioService, CredencialService credencialService) {
        this.scanner = scanner;
        this.usuarioService = usuarioService;
        this.credencialService = credencialService;
    }

    // menu usuarios

    public void gestionarUsuarios() {
        boolean volverMenu = false;

        while (!volverMenu) {
            try {
                MenuDisplay.mostrarMenuUsuarios();
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> crearUsuario();
                    case 2 -> listarUsuarios();
                    case 3 -> buscarUsuarioPorId();
                    case 4 -> actualizarUsuario();
                    case 5 -> eliminarUsuario();
                    case 0 -> volverMenu = true;
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingrese un numero.");
            }
        }
    }

    private void crearUsuario() {
        System.out.println("\n--- CREAR USUARIO ---");

        try {

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("ERROR: El nombre no puede estar vacio.");
                return;
            }

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine().trim();
            if (apellido.isEmpty()) {
                System.out.println("ERROR: El apellido no puede estar vacio.");
                return;
            }

            System.out.print("Edad: ");
            int edad = Integer.parseInt(scanner.nextLine());
            if (edad < 18) {
                System.out.println("ERROR: La edad debe ser mayor o igual a 18.");
                return;
            }

            System.out.print("Email: ");
            String email = scanner.nextLine().trim().toLowerCase();
            if (!validarEmail(email)) {
                System.out.println("ERROR: El email no es valido.");
                return;
            }

            System.out.print("Usuario (username): ");
            String usuario = scanner.nextLine().trim().toUpperCase();
            if (usuario.isEmpty()) {
                System.out.println("ERROR: El usuario no puede estar vacio.");
                return;
            }

            // crear usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setApellido(apellido);
            nuevoUsuario.setEdad(edad);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setUsuario(usuario);
            nuevoUsuario.setEliminado(false);

            System.out.print("Desea crear credencial para este usuario? (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();

            if (respuesta.equals("S")) {
                System.out.print("Contrasenia (min 9 caracteres): ");
                String pass = scanner.nextLine();
                if (pass.length() <= 8) {
                    System.out.println("ADVERTENCIA: La contrasenia debe tener mas de 9 o mas caracteres.");
                    System.out.println("Usuario creado sin credencial.");
                    usuarioService.insertar(nuevoUsuario);
                } else {
                    CredencialAcceso cred = new CredencialAcceso();
                    cred.setFechaCreacion(new Date());
                    cred.setContrasenia(pass);
                    cred.setEliminado(false);

                    nuevoUsuario.setCredencial(cred);

                    // metodo transaccional para crear Usuario + Credencial juntos
                    usuarioService.insertarTx(nuevoUsuario);
                    System.out.println("Credencial creada exitosamente.");
                }
            } else {
                usuarioService.insertar(nuevoUsuario);
            }

            System.out.println("EXITO: Usuario creado con ID: " + nuevoUsuario.getIdUsuario());

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Edad debe ser un numero valido.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUARIOS ---");

        try {
            List<Usuario> activos = usuarioService.getAll();

            if (activos.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
                return;
            }

            System.out.println("\n========================================================================");
            System.out.println("ID | NOMBRE | APELLIDO | EDAD | EMAIL        | USERNAME   | CREDENCIAL");
            System.out.println("--------------------------------------------------------------------------");

            for (Usuario u : activos) {
                String credencial = (u.getCredencial() != null) ? "SI" : "NO";

                System.out.println(u.getIdUsuario() + "  | " +
                        u.getNombre() + " | " +
                        u.getApellido() + " | " +
                        u.getEdad() + " | " +
                        u.getEmail() + " | " +
                        u.getUsuario() + " | " +
                        credencial);
            }

            System.out.println("==========================================================================");
            System.out.println("Total: " + activos.size() + " usuario(s)");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void buscarUsuarioPorId() {
        System.out.print("\nIngrese ID del usuario: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());

            Usuario usuario = usuarioService.getById(id);

            if (usuario == null) {
                System.out.println("Usuario no encontrado.");
                return;
            }

            mostrarDetalleUsuario(usuario);

        } catch (NumberFormatException e) {
            System.out.println("ERROR: ID debe ser un numero.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void actualizarUsuario() {
        System.out.print("\nIngrese ID del usuario a actualizar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());

            Usuario usuario = usuarioService.getById(id);

            if (usuario == null) {
                System.out.println("Usuario no encontrado.");
                return;
            }

            mostrarDetalleUsuario(usuario);

            // actualizar campos
            System.out.print("\nNuevo nombre (ENTER para mantener): ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) {
                usuario.setNombre(nombre);
            }

            System.out.print("Nuevo apellido (ENTER para mantener): ");
            String apellido = scanner.nextLine().trim();
            if (!apellido.isEmpty()) {
                usuario.setApellido(apellido);
            }

            System.out.print("Nueva edad (ENTER para mantener): ");
            String edadStr = scanner.nextLine().trim();
            if (!edadStr.isEmpty()) {
                int edad = Integer.parseInt(edadStr);
                if (edad >= 18) {
                    usuario.setEdad(edad);
                } else {
                    System.out.println("Edad invalida, se mantiene la anterior.");
                }
            }

            System.out.print("Nuevo email (ENTER para mantener): ");
            String email = scanner.nextLine().trim().toLowerCase();
            if (!email.isEmpty()) {
                if (validarEmail(email)) {
                    usuario.setEmail(email);
                } else {
                    System.out.println("Email invalido, se mantiene el anterior.");
                }
            }

            usuarioService.actualizar(usuario);
            System.out.println("EXITO: Usuario actualizado.");

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Entrada invalida.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        System.out.print("\nIngrese ID del usuario a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());

            Usuario usuario = usuarioService.getById(id);

            if (usuario == null) {
                System.out.println("Usuario no encontrado.");
                return;
            }

            mostrarDetalleUsuario(usuario);

            System.out.print("\nConfirmar eliminacion? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S")) {
                usuarioService.eliminar(id);
                System.out.println("EXITO: Usuario eliminado.");
            } else {
                System.out.println("Operacion cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("ERROR: ID debe ser un numero.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // menu credenciales

    public void gestionarCredenciales() {
        boolean volverMenu = false;

        while (!volverMenu) {
            try {
                MenuDisplay.mostrarMenuCredenciales();
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> crearCredencial();
                    case 2 -> listarCredenciales();
                    case 3 -> buscarCredencialPorId();
                    case 4 -> actualizarCredencial();
                    case 5 -> eliminarCredencial();
                    case 0 -> volverMenu = true;
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingrese un numero.");
            }
        }
    }

    private void crearCredencial() {
        System.out.println("\n--- CREAR CREDENCIAL ---");

        try {
            // listausuarios sin credencial
            List<Usuario> todosUsuarios = usuarioService.getAll();
            List<Usuario> sinCredencial = new ArrayList<>();

            for (Usuario u : todosUsuarios) {
                if (u.getCredencial() == null) {
                    sinCredencial.add(u);
                }
            }

            if (sinCredencial.isEmpty()) {
                System.out.println("No hay usuarios sin credencial.");
                return;
            }

            System.out.println("\nUsuarios sin credencial:");
            for (Usuario u : sinCredencial) {
                System.out.println(u.getIdUsuario() + " - " + u.getNombre() + " " + u.getApellido());
            }

            System.out.print("\nIngrese ID del usuario: ");
            int idUsuario = Integer.parseInt(scanner.nextLine());

            // verificar que el usuario existe y no tiene credencial
            Usuario usuario = null;
            for (Usuario u : sinCredencial) {
                if (u.getIdUsuario() == idUsuario) {
                    usuario = u;
                    break;
                }
            }

            if (usuario == null) {
                System.out.println("Usuario no valido.");
                return;
            }

            System.out.print("Contrasenia (min 8 caracteres): ");
            String pass = scanner.nextLine();

            if (pass.length() <= 8) {
                System.out.println("ERROR: La contrasenia debe tener mas de 8 caracteres.");
                return;
            }

            CredencialAcceso cred = new CredencialAcceso();
            cred.setIdUsuario(usuario.getIdUsuario());
            cred.setFechaCreacion(new Date());
            cred.setContrasenia(pass);
            cred.setEliminado(false);

            credencialService.insertar(cred);

            System.out.println("EXITO: Credencial creada con ID: " + cred.getIdCredencial());

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Entrada invalida.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listarCredenciales() {
        System.out.println("\n--- LISTA DE CREDENCIALES ---");

        try {
            List<CredencialAcceso> activas = credencialService.getAll();

            if (activas.isEmpty()) {
                System.out.println("No hay credenciales registradas.");
                return;
            }

            System.out.println("\n====================================================");
            System.out.println("ID CREDENCIAL | ID USER | FECHA CREACION | CONTRASENIA");
            System.out.println("----------------------------------------------------");

            for (CredencialAcceso c : activas) {
                System.out.println(c.getIdCredencial() + "       | " +
                        c.getIdUsuario() + "       | " +
                        c.getFechaCreacion() + "   | ********");
            }

            System.out.println("====================================================");
            System.out.println("Total: " + activas.size() + " credencial(es)");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void buscarCredencialPorId() {
        System.out.print("\nIngrese ID de la credencial: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());

            CredencialAcceso cred = credencialService.getById(id);

            if (cred == null) {
                System.out.println("Credencial no encontrada.");
                return;
            }

            mostrarDetalleCredencial(cred);

        } catch (NumberFormatException e) {
            System.out.println("ERROR: ID debe ser un numero.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void actualizarCredencial() {
        System.out.print("\nIngrese ID de la credencial a actualizar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());

            CredencialAcceso cred = credencialService.getById(id);

            if (cred == null) {
                System.out.println("Credencial no encontrada.");
                return;
            }

            mostrarDetalleCredencial(cred);

            System.out.print("\nNueva contrasenia (min 8 caracteres, ENTER para mantener): ");
            String pass = scanner.nextLine();

            if (!pass.isEmpty()) {
                if (pass.length() > 8) {
                    cred.setContrasenia(pass);
                    credencialService.actualizar(cred);
                    System.out.println("EXITO: Contrasenia actualizada.");
                } else {
                    System.out.println("ERROR: La contrasenia debe tener mas de 8 caracteres.");
                }
            } else {
                System.out.println("Contrasenia sin cambios.");
            }

        } catch (NumberFormatException e) {
            System.out.println("ERROR: ID debe ser un numero.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void eliminarCredencial() {
        System.out.print("\nIngrese ID de la credencial a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());

            CredencialAcceso cred = credencialService.getById(id);

            if (cred == null) {
                System.out.println("Credencial no encontrada.");
                return;
            }

            mostrarDetalleCredencial(cred);

            System.out.print("\nConfirmar eliminacion? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S")) {
                credencialService.eliminar(id);
                System.out.println("EXITO: Credencial eliminada.");
            } else {
                System.out.println("Operacion cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("ERROR: ID debe ser un numero.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // buscar

    public void buscarUsuario() {
        boolean volverMenu = false;

        while (!volverMenu) {
            try {
                MenuDisplay.mostrarMenuBusqueda();
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> buscarPorEmail();
                    case 2 -> buscarPorUsername();
                    case 0 -> volverMenu = true;
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingrese un numero.");
            }
        }
    }

    private void buscarPorEmail() {
        System.out.print("\nIngrese email: ");
        String email = scanner.nextLine().trim().toLowerCase();

        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);

            if (usuario == null) {
                System.out.println("Usuario no encontrado con ese email.");
            } else {
                mostrarDetalleUsuario(usuario);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void buscarPorUsername() {
        System.out.print("\nIngrese username: ");
        String username = scanner.nextLine().trim().toUpperCase();

        try {
            Usuario usuario = usuarioService.buscarPorUsuario(username);

            if (usuario == null) {
                System.out.println("Usuario no encontrado con ese username.");
            } else {
                mostrarDetalleUsuario(usuario);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void mostrarDetalleUsuario(Usuario u) {
        System.out.println("\n--- DETALLE DE USUARIO ---");
        System.out.println("ID: " + u.getIdUsuario());
        System.out.println("Nombre: " + u.getNombre() + " " + u.getApellido());
        System.out.println("Edad: " + u.getEdad());
        System.out.println("Email: " + u.getEmail());
        System.out.println("Username: " + u.getUsuario());

        if (u.getCredencial() != null) {
            System.out.println("Credencial: SI (ID: " + u.getCredencial().getIdCredencial() + ")");
        } else {
            System.out.println("Credencial: NO");
        }
    }

    private void mostrarDetalleCredencial(CredencialAcceso c) {
        System.out.println("\n--- DETALLE DE CREDENCIAL ---");
        System.out.println("ID Credencial: " + c.getIdCredencial());
        System.out.println("ID Usuario: " + c.getIdUsuario());
        System.out.println("Fecha Creacion: " + c.getFechaCreacion());
        System.out.println("Contrasenia: ********");
    }

    // valida que el email tenga un formato basico correcto.

    private boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        int posArroba = email.indexOf('@');
        int posPunto = email.lastIndexOf('.');

        // BUSCAR VALIDACION @ Y PUNTO
        return posArroba > 0 && posPunto > posArroba + 1 && posPunto < email.length() - 1;
    }
}
