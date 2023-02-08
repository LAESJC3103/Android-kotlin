package pacificsoft.pscomandera.Modelo;

import java.io.Serializable;

/**
 * Created by desarrollo ORM on 12/22/2017.
 */

public class Caja implements Serializable {
    private String Codigo = "";
    private String Descripcion = "";

    public Caja() {
    }

    public Caja(String Codigo, String Descripcion) {
        this.Codigo = Codigo;
        this.Descripcion = Descripcion;
    }

    public Caja(Caja caj) {
        this.setCodigo( caj.Codigo);
        this.setDescripcion( caj.getDescripcion() );
    }


    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }


    @Override
    public boolean equals(Object obj) {
        return (obj!=null && obj instanceof Caja && ((Caja)obj).getCodigo().equals(this.getCodigo()));
    }
}
