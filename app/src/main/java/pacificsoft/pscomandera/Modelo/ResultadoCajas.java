package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class ResultadoCajas {
    private String Mensaje;
    private List<Caja> Cajas;

    public ResultadoCajas() {
    }

    public ResultadoCajas(String mensaje, List<Caja> cajas) {
        Mensaje = mensaje;
        Cajas = cajas;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public List<Caja> getCajas() {
        return Cajas;
    }

    public void setCajas(List<Caja> cajas) {
        Cajas = cajas;
    }
}
