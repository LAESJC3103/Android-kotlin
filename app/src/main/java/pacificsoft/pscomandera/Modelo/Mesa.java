package pacificsoft.pscomandera.Modelo;

import java.io.Serializable;

public class Mesa implements Serializable {
    private String codigo;
    private String descripcion;
    private int estado;
    private int numeroCuentas;

    public Mesa() {
    }

    public Mesa(String codigo, String descripcion, int estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public int getNumeroCuentas() {
        return numeroCuentas;
    }

    public void setNumeroCuentas(int numeroCuentas) {
        this.numeroCuentas = numeroCuentas;
    }
}
