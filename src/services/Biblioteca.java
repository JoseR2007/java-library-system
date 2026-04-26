package src.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import src.exceptions.*;
import src.models.Libro;
import src.models.Usuario;

public class Biblioteca {
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

  // Managment functons:
  /**
   * @param titulo (String) Indica el titulo del nuevo libro.
   * @param autor  (String) Indica el autor del nuevo libro.
   */
  public void addNewLibro(String titulo, String autor) {
    Libro nuevo_libro;
    try {
      nuevo_libro = new Libro(titulo, autor);
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
      nuevo_usuario = new Usuario(name);
    } catch (IllegalArgumentException error) {
      throw error;
    }

    this.usuarios.add(nuevo_usuario);
  }

  /**
   * @param idLibro (int) Indica el id del libro que se quiere prestar.
   */
  public void lendBook(int idLibro) {
    if (idLibro < 0)
      throw new IllegalArgumentException("El ID del libro no puede ser engativo");
    if (getLibrobyId(idLibro) == null)
      throw new LibroNotFoundException(idLibro);

    if (this.libros.get(idLibro).isDisponible() == false)
      throw new LibroNotAvailableException(this.libros.get(idLibro).getTitulo());

    this.libros.get(idLibro).setDisponible(false);
  }

  /**
   * @param idLibro (int) Indica el id del libro a devolver.
   */
  public void devolverLibro(int idLibro) {
    if (idLibro < 0)
      throw new IllegalArgumentException("El ID del libro no puede ser negativo");
    if (getLibrobyId(idLibro) == null)
      throw new LibroNotFoundException(idLibro);

    if (!this.libros.get(idLibro).isDisponible())
      this.libros.get(idLibro).setDisponible(true);
  }
}
