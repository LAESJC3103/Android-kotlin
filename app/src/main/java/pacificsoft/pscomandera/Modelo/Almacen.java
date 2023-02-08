package pacificsoft.pscomandera.Modelo;

import java.io.Serializable;

/**
 * Created by desarrollo ORM on 12/21/2017.
 */

public class Almacen implements Serializable{
  private String Id;
  private String Descripcion ="";

  public Almacen(String id, String descripcion) {
    this.Id = id;
    this.Descripcion = descripcion;
  }

  public Almacen(Almacen alm) {
    this.setDescripcion( alm.getDescripcion() );
    this.setId( alm.getId() );
  }


  public String getId() {
    return Id;
  }

  public void setId(String id) {
    this.Id = id;
  }


  public String getDescripcion() {
    return Descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.Descripcion = descripcion;
  }


  @Override
  public boolean equals(Object obj) {
    return (obj!=null && obj instanceof Almacen && ((Almacen)obj).getId().equals(this.getId()));
  }


}
