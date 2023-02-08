package pacificsoft.pscomandera.Modelo;

public class LoginComandera {
    private String usuario;
    private String password;
    private String caja;
    private String tipoVendedor;

    public LoginComandera() {
    }

    public LoginComandera(String usuario, String password, String caja, String tipoVendedor) {
        this.usuario = usuario;
        this.password = password;
        this.caja = caja;
        this.tipoVendedor = tipoVendedor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getTipoVendedor() {
        return tipoVendedor;
    }

    public void setTipoVendedor(String tipoVendedor) {
        this.tipoVendedor = tipoVendedor;
    }
}
