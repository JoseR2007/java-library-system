import java.util.List;
import java.util.Scanner;

import src.models.Libro;
import src.services.*;
import src.exceptions.*;

public class Main {
   private static Biblioteca biblioteca = new Biblioteca();
   private static Scanner scan = new Scanner(System.in);

   public static void main(String[] args) {

      int seleccion;
      do {
         seleccion = menu();

         if (seleccion == 1) {
            agregarLibro();
         } else if (seleccion == 2) {
            agregarUsuario();
         } else if (seleccion == 3) {
            prestarLibro();
         } else if (seleccion == 4) {
            devolverLibro();
         } else if (seleccion == 5) {
            mostrarLibros();
            scan.next();
         } else {
            break;
         }
      } while (seleccion != 6);

      scan.close();
   }

   public static void imprimirSeparador() {
      System.out.println("\n\n\n\n|===============================|");
   }

   public static int menu() {
      int opcion;
      do {
         imprimirSeparador();
         System.out.println(
               "1. Agregar libro\n2. Registrar usuario\n3. Prestar libro\n4. Devolver libro\n5. Mostrar libros\n6. Salir");
         System.out.print("Que deseas hacer? ");
         opcion = scan.nextInt();
         scan.nextLine();
      } while (opcion < 0 || opcion > 6);

      return opcion;
   }

   public static void agregarLibro() {
      imprimirSeparador();
      System.out.print("Titulo: ");
      String title = scan.nextLine();
      System.out.print("Autor: ");
      String autor = scan.nextLine();

      try {
         biblioteca.addNewLibro(title, autor);
      } catch (IllegalArgumentException e) {
         System.out.print("Error: " + e.getMessage());
      }
   }

   public static void agregarUsuario() {
      imprimirSeparador();
      System.out.print("Nombre: ");
      String name = scan.nextLine();

      try {
         biblioteca.addNewUsuario(name);
      } catch (IllegalArgumentException e) {
         System.out.print("Error: " + e.getMessage());
      }
   }

   public static void prestarLibro() {
      if (biblioteca.getLibrosDisponibles().isEmpty()) {
         System.out.println("No existen libros disponibles");
         return;
      }

      int opcion;
      do {
         mostrarLibrosDisponibles();
         System.err.print("\nIngrese el ID del libro a prestar: ");
         opcion = scan.nextInt();
      } while (opcion < 0);

      try {
         biblioteca.lendBook(opcion);
      } catch (LibroNotFoundException e) {
         System.out.println(e.getMessage());
      } catch (LibroNotAvailableException e) {
         System.out.println(e.getMessage() + e.getNameLibro());
      }
   }

   public static void devolverLibro() {
      if (biblioteca.getLibrosOcupados().isEmpty()) {
         System.out.println("No existen libro ocupados");
         return;
      }

      int opcion;
      do {
         mostrarLibrosNoDisponibles();
         System.out.print("\nDigite el ID del libro a devolver: ");
         opcion = scan.nextInt();
      } while (opcion < 0);

      try {
         biblioteca.devolverLibro(opcion);
      } catch (IllegalArgumentException | LibroNotFoundException e) {
         System.out.println(e.getMessage());
      }
   }

   // Mostrar informacion al usuario:

   public static void mostrarLibrosNoDisponibles() {
      imprimirSeparador();
      List<Libro> librosOcupados = biblioteca.getLibrosOcupados();
      if (librosOcupados.isEmpty())
         System.out.println("No existen libros ocupados");

      librosOcupados.forEach(
            libro -> System.out.println(libro.getId() + "\t'" + libro.getTitulo() + "' by " + libro.getAutor()));
   }

   public static void mostrarLibrosDisponibles() {
      imprimirSeparador();
      List<Libro> librosDisponibles = biblioteca.getLibrosDisponibles();
      if (librosDisponibles.isEmpty())
         System.out.println("No existen libros disponibles");

      librosDisponibles
            .forEach(
                  libro -> System.out.println(libro.getId() + "\t'" + libro.getTitulo() + "' by " + libro.getAutor()));
   }

   public static void mostrarLibros() {
      imprimirSeparador();
      List<Libro> libros = biblioteca.getLibros();
      if (libros.isEmpty())
         System.out.println("Aun no sea creado ningun libro");

      for (Libro libroActual : libros) {
         if (libroActual.isDisponible())
            System.out.println(libroActual.getId() + "\t'" + libroActual.getTitulo() + "' by " + libroActual.getAutor()
                  + "\tDisponible");
         else
            System.out.println(libroActual.getId() + "\t'" + libroActual.getTitulo() + "' by " + libroActual.getAutor()
                  + "\tOcupado");
      }
   }
}
