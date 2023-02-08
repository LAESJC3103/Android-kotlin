package pacificsoft.pscomandera.Modelo;
public class ResultadoLoginComandera {
  private String id;
  private String descripcion;
  private Usuario usuario;

  public ResultadoLoginComandera() {
  }

  public ResultadoLoginComandera(String id, String descripcion, Usuario usuario) {
    this.id = id;
    this.descripcion = descripcion;
    this.usuario = usuario;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
}

