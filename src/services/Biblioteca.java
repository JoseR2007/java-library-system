package src.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import src.exceptions.*;
import src.models.Libro;
import src.models.Usuario;

public class Biblioteca {
   private int nextIdLibro = 0;
   private int nextIdUsuario = 0;

   private List<Usuario> usuarios;
   private List<Libro> libros;

   public Biblioteca() {
      this.usuarios = new ArrayList<Usuario>();
      this.libros = new ArrayList<Libro>();
   }

   // Getters:
   /**
    * @return (List<Usuario>) Retorna una copia a la lista de los usuarios
    *         existentes.
    */
   public List<Usuario> getUsuarios() {
      return new ArrayList<>(this.usuarios);
   }

   /**
    * @return (List<Libro>) Retorna una copia a la lista de los libros existentes.
    */
   public List<Libro> getLibros() {
      return new ArrayList<>(this.libros);
   }

   public Libro getLibrobyId(int id) {
      if (id < 0)
         throw new IllegalArgumentException("El ID debe ser un numero positivo");

      for (Libro libroActual : this.libros) {
         if (libroActual.getId() == id)
            return libroActual;
      }

      return null;
   }

   /**
    * @return (List<Libro>) Retorna los libros que se encuentren diponibles
    *         (state = true).
    */
   public List<Libro> getLibrosDisponibles() {
      return this.libros.stream().filter(libro -> libro.isDisponible()).collect(Collectors.toList());
   }

   /**
    * @return (List<Libro>) Retorna los libros que NO se encuentran disponbles
    *         (state = false).
    */
   public List<Libro> getLibrosOcupados() {
      return this.libros.stream().filter(libro -> !libro.isDisponible()).collect(Collectors.toList());
   }

   /**
    * @param usuario (Usuario) Indica el usuario a buscar
    * @return (List<Libro>) Retorna una lista con los libros de dicho usuario
    */
   public List<Libro> getLibrosByUser(Usuario usuario) {
      return this.libros.stream()
            .filter(libro -> {
               if (libro.getUsuario() != null) {
                  if (libro.getUsuario().getId() == usuario.getId())
                     return true;
               }

               return false;
            })
            .collect(Collectors.toList());
   }

   /**
    * @param id (int) Indica el id del usuario
    * @return (Usuario) Devuelve el usuario con el id rescibido si existe
    */
   public Usuario getUsuarioById(int id) {
      for (Usuario user : this.usuarios)
         if (user.getId() == id)
            return user;

      return null;
   }

   // Managment functons:
   /**
    * @param titulo (String) Indica el titulo del nuevo libro.
    * @param autor  (String) Indica el autor del nuevo libro.
    */
   public void addNewLibro(String titulo, String autor) {
      Libro nuevo_libro;
      try {
         nuevo_libro = new Libro(this.nextIdLibro++, titulo, autor);
      } catch (IllegalArgumentException error) {
         throw error;
      }

      this.libros.add(nuevo_libro);
   }

   /**
    * @param name (String) Indica el nombre del nuevo usuario.
    */
   public void addNewUsuario(String name) {
      Usuario nuevo_usuario;
      try {
         nuevo_usuario = new Usuario(this.nextIdUsuario++, name);
      } catch (IllegalArgumentException error) {
         throw error;
      }

      this.usuarios.add(nuevo_usuario);
   }

   /**
    * @param idLibro (int) Indica el id del libro que se quiere prestar.
    */
   public void lendBook(int idLibro, Usuario nuevoUsuario) {
      if (idLibro < 0)
         throw new IllegalArgumentException("El ID del libro no puede ser engativo");
      if (getLibrobyId(idLibro) == null)
         throw new LibroNotFoundException(idLibro);

      Libro libroBuscado = getLibrobyId(idLibro);
      if (libroBuscado.isDisponible() == false)
         throw new LibroNotAvailableException(libroBuscado.getTitulo());

      libroBuscado.prestarLibro(nuevoUsuario);
   }

   /**
    * @param idLibro (int) Indica el id del libro a devolver.
    */
   public void devolverLibro(int idLibro) {
      if (idLibro < 0)
         throw new IllegalArgumentException("El ID del libro no puede ser negativo");
      if (getLibrobyId(idLibro) == null)
         throw new LibroNotFoundException(idLibro);

      getLibrobyId(idLibro).devolverLibro();
   }
}
