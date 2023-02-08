package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class Grupo {

    private int id;
    private String descripcion;
    private List<Platillo> Platillos;

    public Grupo() {
    }

    public Grupo(int id, String descripcion, List<Platillo> platillos) {
        this.id = id;
        this.descripcion = descripcion;
        Platillos = platillos;
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

    public List<Platillo> getPlatillos() {
        return Platillos;
    }

    public void setPlatillos(List<Platillo> platillos) {
        Platillos = platillos;
    }
}
