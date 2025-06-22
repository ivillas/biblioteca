package com.practica;

import com.practica.controlador.BibliotecaController;
import com.practica.modelo.Autor;
import com.practica.modelo.Libro;
import com.practica.modelo.Prestamo;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        BibliotecaController controller = new BibliotecaController();
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            System.out.println("\n--- MENÚ BIBLIOTECA ---");
            System.out.println("1. Agregar autor");
            System.out.println("2. Agregar libro");
            System.out.println("3. Crear préstamo");
            System.out.println("4. Devolver libro");
            System.out.println("5. Listar autores");
            System.out.println("6. Listar libros");
            System.out.println("7. Listar préstamos");
            System.out.println("8. Consultar por ID");
            System.out.println("9. Salir");
            System.out.print("Elige una opción: ");
            String opcion = sc.nextLine();
            try {
                switch (opcion) {
                    case "1":
                        System.out.print("ID autor: ");
                        String idAutor = sc.nextLine();
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Nacionalidad: ");
                        String nacionalidad = sc.nextLine();
                        System.out.print("Año nacimiento: ");
                        int anio = Integer.parseInt(sc.nextLine());
                        controller.agregarAutor(new Autor(idAutor, nombre, nacionalidad, anio));
                        System.out.println("Autor agregado.");
                        break;
                    case "2":
                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("ID autor: ");
                        String idAutorLibro = sc.nextLine();
                        Autor autorLibro = controller.buscarAutor(idAutorLibro);
                        if (autorLibro == null) {
                            System.out.println("Autor no encontrado.");
                            break;
                        }
                        System.out.print("Año publicación: ");
                        int anioPub = Integer.parseInt(sc.nextLine());
                        controller.agregarLibro(new Libro(isbn, titulo, autorLibro, anioPub));
                        System.out.println("Libro agregado.");
                        break;
                    case "3":
                        System.out.print("ID préstamo: ");
                        String idPrestamo = sc.nextLine();
                        System.out.print("ISBN libro: ");
                        String isbnPrestamo = sc.nextLine();
                        System.out.print("Nombre usuario: ");
                        String usuario = sc.nextLine();
                        System.out.print("Fecha préstamo (yyyy-MM-dd): ");
                        Date fechaPrestamo = sdf.parse(sc.nextLine());
                        System.out.print("Fecha devolución (yyyy-MM-dd): ");
                        Date fechaDevolucion = sdf.parse(sc.nextLine());
                        boolean prestado = controller.prestarLibro(idPrestamo, isbnPrestamo, usuario, fechaPrestamo, fechaDevolucion);
                        System.out.println(prestado ? "Préstamo realizado." : "No se pudo realizar el préstamo.");
                        break;
                    case "4":
                        System.out.print("ID préstamo a devolver: ");
                        String idDev = sc.nextLine();
                        boolean devuelto = controller.devolverLibro(idDev);
                        System.out.println(devuelto ? "Libro devuelto." : "No se pudo devolver el libro.");
                        break;
                    case "5":
                        List<Autor> autores = controller.listarAutores();
                        autores.forEach(System.out::println);
                        break;
                    case "6":
                        List<Libro> libros = controller.listarLibros();
                        libros.forEach(System.out::println);
                        break;
                    case "7":
                        List<Prestamo> prestamos = controller.listarPrestamos();
                        prestamos.forEach(System.out::println);
                        break;
                    case "8":
                        System.out.println("a) Consultar autor");
                        System.out.println("b) Consultar libro");
                        System.out.println("c) Consultar préstamo");
                        System.out.print("Elige opción: ");
                        String sub = sc.nextLine();
                        switch (sub) {
                            case "a":
                                System.out.print("ID autor: ");
                                Autor a = controller.buscarAutor(sc.nextLine());
                                System.out.println(a != null ? a : "No encontrado.");
                                break;
                            case "b":
                                System.out.print("ISBN libro: ");
                                Libro l = controller.buscarLibro(sc.nextLine());
                                System.out.println(l != null ? l : "No encontrado.");
                                break;
                            case "c":
                                System.out.print("ID préstamo: ");
                                Prestamo p = controller.buscarPrestamo(sc.nextLine());
                                System.out.println(p != null ? p : "No encontrado.");
                                break;
                        }
                        break;
                    case "9":
                        System.out.println("¡Hasta luego!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}