package pacificsoft.pscomandera.Modelo;

public class Terminal {
    private String cliente;
    private String serie;
    private String proveedor;
    private String terminal;

    public Terminal (){

    }


    public Terminal(String cliente, String serie, String proveedor, String terminal) {
        this.cliente = cliente;
        this.serie = serie;
        this.proveedor = proveedor;
        this.terminal = terminal;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}
