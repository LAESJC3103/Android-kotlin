package pacificsoft.pscomandera.Enum;

public enum EnumTipoAlmacen {
  MERCANCIA(1,"Mercancia"),
  MATERIA_PRIMA(2,"Materia prima");

  private int id;
  private String descripcion;

  EnumTipoAlmacen(int id, String descripcion) {
    this.id = id;
    this.descripcion = descripcion;
  }

  public int getId() {
    return id;
  }

  public String getDescripcion() {
    return descripcion;
  }
}
