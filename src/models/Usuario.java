package src.models;

public class Usuario {
  private static int cantUsuarios = 0; // Cantidad de Usuarios existentes en la app.

  private int id;
  private String name;

  /**
   * @param name (String) Indica el nombre del usuario.
   */
  public Usuario(String name) {
    if (name == null || name.isEmpty())
      throw new IllegalArgumentException("Nombre de usuario invalido (vacio)");

    this.name = name;
    this.id = cantUsuarios;
    cantUsuarios++;
  }

  /**
   * @return (String) Retorna el nombre del usuario.
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return (String) Retorna el ID del usuario.
   */
  public int getId() {
    return this.id;
  }
}
