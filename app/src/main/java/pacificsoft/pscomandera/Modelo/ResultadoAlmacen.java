package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class ResultadoAlmacen {
  private String Mensaje;
  private List<Almacen> Almacenes;

  public ResultadoAlmacen() {
  }

  public ResultadoAlmacen(String mensaje, List<Almacen> almacenes) {
    Mensaje = mensaje;
    Almacenes = almacenes;
  }

  public String getMensaje() {
    return Mensaje;
  }

  public void setMensaje(String mensaje) {
    Mensaje = mensaje;
  }

  public List<Almacen> getAlmacenes() {
    return Almacenes;
  }

  public void setCajas(List<Almacen> almacenes) {
    Almacenes = almacenes;
  }
}
