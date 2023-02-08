package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class DatosRegresados {

  private List<?> lstDatosRegresados;
  private Object datosRegresados;
  private String datosRegresadosString;
  private Boolean error;
  private String msjError;
  private Integer numeroError;
  private String msj;

    /*
    public DatosRegresados(List<?> lstDatosRegresados,Object datosRegresados,Boolean error,String msjError,Integer numeroError){
        this.lstDatosRegresados = lstDatosRegresados;
        this.datosRegresados = datosRegresados;
        this.error = error;
        this.msjError = msjError;
        this.numeroError = numeroError;
    }*/


  public List<?> getLstDatosRegresados() {
    return lstDatosRegresados;
  }

  public Object getDatosRegresados() {
    return datosRegresados;
  }

  public Boolean getError() {
    return error;
  }

  public String getMsjError() {
    return msjError;
  }

  public Integer getNumeroError() {
    return numeroError;
  }

  public void setLstDatosRegresados(List<?> lstDatosRegresados) {
    this.lstDatosRegresados = lstDatosRegresados;
  }

  public void setDatosRegresados(Object datosRegresados) {
    this.datosRegresados = datosRegresados;
  }

  public void setError(Boolean error) {
    this.error = error;
  }

  public void setMsjError(String msjError) {
    this.msjError = msjError;
  }

  public void setNumeroError(Integer numeroError) {
    this.numeroError = numeroError;
  }

  public String getMsj() {
    return msj;
  }

  public void setMsj(String msj) {
    this.msj = msj;
  }

  public String getDatosRegresadosString() {
    return datosRegresadosString;
  }

  public void setDatosRegresadosString(String datosRegresadosString) {
    this.datosRegresadosString = datosRegresadosString;
  }
}
