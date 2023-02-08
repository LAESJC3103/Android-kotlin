package pacificsoft.pscomandera.Modelo;

public class Aplicacion {

  private String nombreAplicacion;
  private String direccionAplicacion;
  private int iconoAplicacion;
  private String descripcion;

  public Aplicacion(String nombreAplicacion, String direccionAplicacion, int iconoAplicacion,String descripcion) {
    this.nombreAplicacion = nombreAplicacion;
    this.direccionAplicacion = direccionAplicacion;
    this.iconoAplicacion = iconoAplicacion;
    this.descripcion = descripcion;
  }

  public String getNombreAplicacion() {
    return nombreAplicacion;
  }

  public void setNombreAplicacion(String nombreAplicacion) {
    this.nombreAplicacion = nombreAplicacion;
  }

  public String getDireccionAplicacion() {
    return direccionAplicacion;
  }

  public void setDireccionAplicacion(String direccionAplicacion) {
    this.direccionAplicacion = direccionAplicacion;
  }

  public int getIconoAplicacion() {
    return iconoAplicacion;
  }

  public void setIconoAplicacion(int iconoAplicacion) {
    this.iconoAplicacion = iconoAplicacion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
}
