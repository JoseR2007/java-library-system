package src.services;

import java.util.ArrayList;
import java.util.List;

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
   * @param idLibro (int) Indica el id del libro a buscar
   * @return (boolean) Retorna un booleano que indica si existe o no el libro
   */
  protected boolean existBook(int idLibro) {
    for (Libro libroActual : this.libros) {
      if (libroActual.getId() == idLibro)
        return true;
    }

    return false;
  }

  /**
   * @param idLibro (int) Indica el id del libro que se quiere prestar.
   */
  public void lendBook(int idLibro) {
    if (idLibro < 0)
      throw new IllegalArgumentException("El ID del libro no puede ser engativo");
    if (!existBook(idLibro))
      throw new LibroNotFoundException(idLibro);

    if (this.libros.get(idLibro).getState() == false)
      throw new LibroNotAvailableException(this.libros.get(idLibro).getTitulo());

    this.libros.get(idLibro).setState(false);
  }

  /**
   * @param idLibro (int) Indica el id del libro a devolver.
   */
  public void devolverLibro(int idLibro) {
    if (idLibro < 0)
      throw new IllegalArgumentException("El ID del libro no puede ser negativo");
    if (!existBook(idLibro))
      throw new LibroNotFoundException(idLibro);

    if (!this.libros.get(idLibro).getState())
      this.libros.get(idLibro).setState(true);
  }
}
