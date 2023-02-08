package pacificsoft.pscomandera.Modelo;

public class Nota {
    private int Id;
    private String Descripcion;

    public Nota() {
    }

    public Nota(int id, String descripcion) {
        Id = id;
        Descripcion = descripcion;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
