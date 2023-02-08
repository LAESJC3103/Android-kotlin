package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class Area {
    private String codigo;
    private String  area;
    private List<Mesa> Mesas;

    public Area (String codigo,String area,List<Mesa> Mesas){
        this.codigo = codigo;
        this.area = area;
        this.Mesas = Mesas;
    }


    public String getCodigo() {
        return codigo;
    }

    public String getArea() {
        return area;
    }

    public List<Mesa> getMesas() {
        return Mesas;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setMesas(List<Mesa> mesas) {
        Mesas = mesas;
    }


    @Override
    public boolean equals( Object obj) {
        return obj!=null && obj instanceof Area && ((Area)obj).getCodigo().equals(this.codigo);
    }
}
