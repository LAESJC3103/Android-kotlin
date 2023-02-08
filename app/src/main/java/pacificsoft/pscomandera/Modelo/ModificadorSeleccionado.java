package pacificsoft.pscomandera.Modelo;

import java.io.Serializable;
import java.util.List;

public class ModificadorSeleccionado implements Serializable {
    private String orden;;
    private String tipo;
    private int codlista;
    private List<Modificador> modificadoresSeleccionados;

    public ModificadorSeleccionado() {
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCodlista() {
        return codlista;
    }

    public void setCodlista(int codlista) {
        this.codlista = codlista;
    }

    public List<Modificador> getModificadoresSeleccionados() {
        return modificadoresSeleccionados;
    }

    public void setModificadoresSeleccionados(List<Modificador> modificadoresSeleccionados) {
        this.modificadoresSeleccionados = modificadoresSeleccionados;
    }
}
