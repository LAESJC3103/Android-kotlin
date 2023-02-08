package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class ResultadoGenerico {
  private String Mensaje;
  private String Estatus;
  private String idRespuesta;
  private List<Object> lstObjetoRegresado;


  public ResultadoGenerico() {
  }

  public ResultadoGenerico(String mensaje, String estatus, String idRespuesta) {
    Mensaje = mensaje;
    Estatus = estatus;
    this.idRespuesta = idRespuesta;
  }

  public String getMensaje() {
    return Mensaje;
  }

  public void setMensaje(String mensaje) {
    Mensaje = mensaje;
  }

  public String getEstatus() {
    return Estatus;
  }

  public void setEstatus(String estatus) {
    Estatus = estatus;
  }

  public String getIdRespuesta() {
    return idRespuesta;
  }

  public void setIdRespuesta(String idRespuesta) {
    this.idRespuesta = idRespuesta;
  }

  public List<Object> getLstObjetoRegresado() {
    return lstObjetoRegresado;
  }

  public void setLstObjetoRegresado(List<Object> lstObjetoRegresado) {
    this.lstObjetoRegresado = lstObjetoRegresado;
  }
}
