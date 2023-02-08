package pacificsoft.pscomandera.Modelo;

import java.io.Serializable;
import java.util.List;

public class Receta implements Serializable {
    private String ID;
    private List<Modificador> Listas;

    public Receta() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<Modificador> getListas() {
        return Listas;
    }

    public void setListas(List<Modificador> listas) {
        Listas = listas;
    }
}
