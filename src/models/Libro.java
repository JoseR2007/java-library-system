package src.models;

import src.exceptions.LibroNotAvailableException;

public class Libro {
  private int id;
  private String titulo;
  private String autor;
  private boolean disponible = true;
  private Usuario usuarioPrestado = null;

  /**
   * @param titulo (String) El titulo del libro
   * @param autor  (String) El autor del libro
   */
  public Libro(int id, String titulo, String autor) {
    if (titulo == null || titulo.isEmpty())
      throw new IllegalArgumentException("El titulo ingresado NO es valido");
    if (autor == null || autor.isEmpty())
      throw new IllegalArgumentException("El autor recibido no es valido");
    if (id < 0)
      throw new IllegalArgumentException("El id no puede ser negativo");

    this.titulo = titulo;
    this.autor = autor;
    this.id = id;
  }

  // ====== Getters ======

  /**
   * @return (int) Retorna el ID del libro
   */
  public int getId() {
    return this.id;
  }

  /**
   * @return (String) Retorna el titulo del libro
   */
  public String getTitulo() {
    return this.titulo;
  }

  /**
   * @return (String) Retorna el autor del libro
   */
  public String getAutor() {
    return this.autor;
  }

  /**
   * @return (Libro) Retorna el usuario al que fue prestado el libro
   */
  public Usuario getUsuario() {
    return this.usuarioPrestado;
  }

  /**
   * @return (boolean) Retorna el estado actual del libro (disponible o no
   *         disponible)
   */
  public boolean isDisponible() {
    return this.disponible;
  }

  // ====== Setters ======

  public void prestarLibro(Usuario nuevoUsuario) {
    if (nuevoUsuario.getName().isEmpty())
      throw new IllegalArgumentException("El nuevo usuario no puede tener propiedades vacias.");

    if (this.disponible == true)
      throw new LibroNotAvailableException(this.titulo);

    this.usuarioPrestado = nuevoUsuario;
    this.disponible = false;
  }

  public void devolverLibro() {
    this.usuarioPrestado = null;
    this.disponible = true;
  }

}
