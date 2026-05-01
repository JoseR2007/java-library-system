import java.util.List;
import java.util.Scanner;

import src.models.Libro;
import src.models.Usuario;
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
      imprimirSeparador();
      int ind;
      do {
         mostrarUsuarios();
         System.out.print("Digite el id del usuario que necesita el libro: ");
         ind = scan.nextInt();
      } while (ind < 0);

      Usuario selUsuario = biblioteca.getUsuarioById(ind);
      if (selUsuario == null) {
         System.out.println("Ese usuario no esta disponible");
         return;
      }

      do {
         mostrarLibrosDisponibles();
         System.out.print("Digite el id del libro a prestar: ");
         ind = scan.nextInt();
      } while (ind < 0);

      try {
         biblioteca.lendBook(ind, selUsuario);
      } catch (LibroNotAvailableException | LibroNotFoundException e) {
         System.out.println(e.getMessage());
         return;
      }
   }

   public static void devolverLibro() {
      imprimirSeparador();
      int ind;
      do {
         mostrarUsuarios();
         System.out.print("Digite el id del usuario que posee el libro: ");
         ind = scan.nextInt();
      } while (ind < 0);

      Usuario usuarioSeleccionado = biblioteca.getUsuarioById(ind);
      if (usuarioSeleccionado == null) {
         System.out.println("Ese usuario no existe");
         return;
      }

      do {
         mostrarLibrosDeUnUsuario(usuarioSeleccionado);
         System.out.print("Digite el id el libro a devolver");
         ind = scan.nextInt();
      } while (ind < 0);

      try {
         biblioteca.devolverLibro(ind);
      } catch (LibroNotFoundException e) {
         System.out.println(e.getMessage());
         return;
      }
   }

   // Mostrar informacion al usuario:

   public static void mostrarLibrosDeUnUsuario(Usuario usuario) {
      List<Libro> libros = biblioteca.getLibrosByUser(usuario);
      if (libros.isEmpty()) {
         System.out.println("Este usuario no tiene libros agregados");
         return;
      }

      libros.forEach(
            libro -> System.out.println(libro.getId() + "\t'" + libro.getTitulo() + "' by " + libro.getAutor()));
   }

   public static void mostrarLibrosDisponibles() {
      imprimirSeparador();
      List<Libro> librosDisponibles = biblioteca.getLibrosDisponibles();
      if (librosDisponibles.isEmpty()) {
         System.out.println("No existen libros disponibles");
         return;
      }

      librosDisponibles.forEach(
            libro -> System.out.println(libro.getId() + "\t'" + libro.getTitulo() + "' by " + libro.getAutor()));
   }

   public static void mostrarLibros() {
      imprimirSeparador();
      List<Libro> libros = biblioteca.getLibros();
      if (libros.isEmpty()) {
         System.out.println("Aun no sea creado ningun libro");
         return;
      }

      for (Libro libroActual : libros) {
         if (libroActual.isDisponible())
            System.out.println(libroActual.getId() + "\t'" + libroActual.getTitulo() + "' by " + libroActual.getAutor()
                  + "\tDisponible");
         else
            System.out.println(libroActual.getId() + "\t'" + libroActual.getTitulo() + "' by " + libroActual.getAutor()
                  + "\tOcupado");
      }
   }

   public static void mostrarUsuarios() {
      imprimirSeparador();
      List<Usuario> usuarios = biblioteca.getUsuarios();
      if (usuarios.isEmpty()) {
         System.out.println("Aun no existen usuarios");
         return;
      }

      usuarios.forEach(usuario -> System.out.println(usuario.getId() + "\t" + usuario.getName()));
   }
}
