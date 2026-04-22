package src.exceptions;

public class LibroNotFoundException extends RuntimeException {
  private final int idLibroNotFound;

  /**
   * @param idLibroNotFound (int) Indica el id del libro no encontrado.
   */
  public LibroNotFoundException(int idLibroNotFound) {
    super("El ID indicado no corresponde a ningun libro existente. " + idLibroNotFound);
    this.idLibroNotFound = idLibroNotFound;
  }

  /**
   * @return (int) Retorna el id del libro no encontrado.
   */
  public int getIdLibroNotFound() {
    return this.idLibroNotFound;
  }
}
