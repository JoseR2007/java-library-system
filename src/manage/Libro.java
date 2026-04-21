package src.manage;

public class Libro {
  public static int cantLibros = 0; // Cantidad de libros existentes en la app

  private int id;
  private String titulo;
  private String autor;
  private boolean disponible = true;

  /**
   * @param titulo (String) El titulo del libro
   * @param autor  (String) El autor del libro
   */
  public Libro(String titulo, String autor) {
    if (titulo == null || titulo.isEmpty())
      throw new IllegalArgumentException("El titulo ingresado NO es valido");
    if (autor == null || autor.isEmpty())
      throw new IllegalArgumentException("El autor recibido no es valido");

    this.titulo = titulo;
    this.autor = autor;
    this.id = cantLibros;

    cantLibros++;
  }

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
   * @return (boolean) Retorna el estado actual del libro (disponible o no
   *         disponible)
   */
  public boolean getState() {
    return this.disponible;
  }

  /**
   * @param newState (boolean) El nuevo estado del libro
   */
  public void setState(boolean newState) {
    this.disponible = newState;
  }

}
