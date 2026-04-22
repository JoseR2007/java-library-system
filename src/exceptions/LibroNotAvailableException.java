package src.exceptions;

public class LibroNotAvailableException extends RuntimeException {
  private final String nameLibro;

  /**
   * @param name (String) Indica el nombre del libro no disponible
   */
  public LibroNotAvailableException(String name) {
    super("El libro no se encuentra disponible en este momento");
    this.nameLibro = name;
  }

  /**
   * @return (String) Retorna el nombre del libro no disponible.
   */
  public String getNameLibro() {
    return this.nameLibro;
  }
}