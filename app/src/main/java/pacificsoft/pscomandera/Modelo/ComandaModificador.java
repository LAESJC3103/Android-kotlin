package pacificsoft.pscomandera.Modelo;

import java.io.Serializable;

public class ComandaModificador implements Serializable {
    private String id;
    private int cantidad;
    private  int codlista;

    public ComandaModificador() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodlista() {
        return codlista;
    }

    public void setCodlista(int codlista) {
        this.codlista = codlista;
    }
}