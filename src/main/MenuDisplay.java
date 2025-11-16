/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author faacu
 */
public class MenuDisplay {

/**
 * Clase utilitaria para mostrar los menus de la aplicacion.
 * Solo contiene metodos estaticos de visualizacion (no tiene estado).
 */

    
    /**
     * Muestra el menu principal con todas las opciones disponibles.
     */
    public static void mostrarMenuPrincipal() {
        System.out.println("\n===== SISTEMA DE USUARIOS Y CREDENCIALES =====");
        System.out.println("1. Gestionar Usuarios");
        System.out.println("2. Gestionar Credenciales");
        System.out.println("3. Buscar Usuario");
        System.out.println("0. Salir");
        System.out.print("Ingrese una opcion: ");
    }
    
    /**
     * Muestra el submenu de gestion de usuarios.
     */
    public static void mostrarMenuUsuarios() {
        System.out.println("\n===== GESTION DE USUARIOS =====");
        System.out.println("1. Crear Usuario");
        System.out.println("2. Listar Todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Actualizar Usuario");
        System.out.println("5. Eliminar Usuario");
        System.out.println("0. Volver");
        System.out.print("Ingrese una opcion: ");
    }
    
    /**
     * Muestra el submenu de gestion de credenciales.
     */
    public static void mostrarMenuCredenciales() {
        System.out.println("\n===== GESTION DE CREDENCIALES =====");
        System.out.println("1. Crear Credencial");
        System.out.println("2. Listar Todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Actualizar Credencial");
        System.out.println("5. Eliminar Credencial");
        System.out.println("0. Volver");
        System.out.print("Ingrese una opcion: ");
    }
    
    /**
     * Muestra el submenu de busqueda de usuarios.
     */
    public static void mostrarMenuBusqueda() {
        System.out.println("\n===== BUSCAR USUARIO =====");
        System.out.println("1. Buscar por Email");
        System.out.println("2. Buscar por Username");
        System.out.println("0. Volver");
        System.out.print("Ingrese una opcion: ");
    }
}

