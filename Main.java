import java.util.List;
import java.util.Scanner;

import src.models.Libro;
import src.services.*;
import src.exceptions.*;

public class Main {
   private static Biblioteca biblioteca = new Biblioteca();
   private static Scanner scan = new Scanner(System.in);

   public static void main(String[] args) {

   }

   public static void agregarLibro() {
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
      System.out.print("Nombre: ");
      String name = scan.nextLine();

      try {
         biblioteca.addNewUsuario(name);
      } catch (IllegalArgumentException e) {
         System.out.print("Error: " + e.getMessage());
      }
   }

   public static void prestarLibro() {
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
      int opcion;
      do {
         mostrarLibrosNoDisponibles();
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
      List<Libro> librosOcupados = biblioteca.getLibrosOcupados();
      if (librosOcupados.isEmpty())
         System.out.println("No existen libros ocupados");

      librosOcupados.forEach(
            libro -> System.out.println(libro.getId() + "\t'" + libro.getTitulo() + "' by " + libro.getAutor()));
   }

   public static void mostrarLibrosDisponibles() {
      List<Libro> librosDisponibles = biblioteca.getLibrosDisponibles();
      if (librosDisponibles.isEmpty())
         System.out.println("No existen libros disponibles");

      librosDisponibles
            .forEach(
                  libro -> System.out.println(libro.getId() + "\t'" + libro.getTitulo() + "' by " + libro.getAutor()));
   }

   public static void mostrarLibros() {
      List<Libro> libros = biblioteca.getLibros();
      if (libros.isEmpty())
         System.out.println("Aun no sea creado ningun libro");

      for (Libro libroActual : libros) {
         if (libroActual.getState())
            System.out.println(libroActual.getId() + "'" + libroActual.getTitulo() + "' by " + libroActual.getAutor()
                  + "\tDisponible");
         else
            System.out.println(libroActual.getId() + "'" + libroActual.getTitulo() + "' by " + libroActual.getAutor()
                  + "\tOcupado");
      }
   }
}
