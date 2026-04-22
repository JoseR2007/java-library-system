package src.exceptions;

public class UsuarioNotFoundException extends RuntimeException {
  private final int idUserNotFound;

  /**
   * @param idUserNotFound (int) Indica el id del usuario no encontrado.
   */
  public UsuarioNotFoundException(int idUserNotFound) {
    super("El ID indicado no corresponde a ningun usuario. " + idUserNotFound);
    this.idUserNotFound = idUserNotFound;
  }

  /**
   * @return (int) Retorna el id del usuario no encontrado.
   */
  public int getIdUserNotFound() {
    return this.idUserNotFound;
  }
}
