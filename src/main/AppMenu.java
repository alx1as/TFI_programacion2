package main;

import java.util.Scanner;
import dao.UsuarioDao;
import dao.CredencialAccesoDao;
import services.UsuarioService;
import services.CredencialService;
/**
 *
 * @author faacu
 */
public class AppMenu {
   
/**
 * Orquestador principal del menu de la aplicacion.
 * Gestiona el ciclo de vida del menu y coordina todas las dependencias.
 */
    private final Scanner scanner;
    private final MenuHandler menuHandler;
    private boolean running;
    
    /**
     * Constructor que inicializa la aplicacion con la cadena de dependencias completa.
     */
    public AppMenu() {
        this.scanner = new Scanner(System.in);
        
        // Crear DAOs
        CredencialAccesoDao credencialDao = new CredencialAccesoDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        
        // Crear Services (con inyeccion de dependencias)
        CredencialService credencialService = new CredencialService(credencialDao);
        UsuarioService usuarioService = new UsuarioService(usuarioDao, credencialService);
        
        // Crear MenuHandler con Services
        this.menuHandler = new MenuHandler(scanner, usuarioService, credencialService);
        this.running = true;
    }
    
    /**
     * Punto de entrada de la aplicacion Java.
     */
    public static void main(String[] args) {
        AppMenu app = new AppMenu();
        app.run();
    }
    
    /**
     * Loop principal del menu.
     */
    public void run() {
        System.out.println("\n*** BIENVENIDO AL SISTEMA ***");
        
        while (running) {
            try {
                MenuDisplay.mostrarMenuPrincipal();
                int opcion = Integer.parseInt(scanner.nextLine());
                processOption(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, ingrese un numero.");
            }
        }
        
        System.out.println("\nGracias por usar el sistema. Adios!");
        scanner.close();
    }
    
    /**
     * Procesa la opcion seleccionada por el usuario.
     */
    private void processOption(int opcion) {
        switch (opcion) {
            case 1 -> menuHandler.gestionarUsuarios();
            case 2 -> menuHandler.gestionarCredenciales();
            case 3 -> menuHandler.buscarUsuario();
            case 0 -> {
                System.out.println("Saliendo del sistema...");
                running = false;
            }
            default -> System.out.println("Opcion no valida. Intente nuevamente.");
        }
    }
}

