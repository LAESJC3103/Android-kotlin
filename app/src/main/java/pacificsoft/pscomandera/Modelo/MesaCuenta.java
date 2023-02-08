package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class MesaCuenta {

    private String Mesa;
    private List<Cuenta> Cuentas;

    public MesaCuenta() {
    }

    public String getMesa() {
        return Mesa;
    }

    public void setMesa(String mesa) {
        Mesa = mesa;
    }

    public List<Cuenta> getCuentas() {
        return Cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        Cuentas = cuentas;
    }
}
