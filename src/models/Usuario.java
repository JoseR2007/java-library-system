package src.models;

public class Usuario {
  private int id;
  private String name;

  /**
   * @param name (String) Indica el nombre del usuario.
   */
  public Usuario(int id, String name) {
    if (name == null || name.isEmpty())
      throw new IllegalArgumentException("Nombre de usuario invalido (vacio)");

    if (id < 0)
      throw new IllegalArgumentException("El id no puede ser negativo");

    this.name = name;
    this.id = id;
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
