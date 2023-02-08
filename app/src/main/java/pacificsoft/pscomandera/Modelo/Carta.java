package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class Carta {
    private int id;
    private String descripcion;
    private List<Grupo> Grupos;

    public Carta() {
    }

    public Carta(int id, String descripcion, List<Grupo> grupos) {
        this.id = id;
        this.descripcion = descripcion;
        Grupos = grupos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Grupo> getGrupos() {
        return Grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        Grupos = grupos;
    }
}
