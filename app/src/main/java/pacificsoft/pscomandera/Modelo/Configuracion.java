package pacificsoft.pscomandera.Modelo;

import java.io.Serializable;

import pacificsoft.pscomandera.Enum.EnumModeloImpresora;

/**
 * Created by desarrollo ORM on 12/22/2017.
 */

public class Configuracion implements Serializable {
    private int id=0;
    private String empresa = "";
    private String domicilio = "";
    private String numero = "";
    private String colonia = "";
    private String rfc = "";
    private String cp = "";
    private String endPointServicioPrincipal = "";
    private int tipovendedor = 0;
    private String caja = "";
    private String almacenMercancia = "";
    private String almacenMateriaPrima = "";
    private String impresora = "";
    private String endPointServicioPagoTerminal = "";
    private String terminal = "";
    private String numeroSeriePinpad = "";
    private String direccionBTPinpad = "";
    private EnumModeloImpresora modeloImpresora;
    private String correoElectronicoPagoTarjeta = "" ;
    private String proveedor = "";


    public Configuracion(int id, String empresa, String domicilio, String numero, String colonia, String rfc, String cp, String endPointServicioPrincipal, int tipovendedor, String caja, String almacenMercancia,String almacenMateriaPrima, String impresora, String endPointServicioPagoTerminal, String terminal, String numeroSeriePinpad, String direccionBTPinpad, EnumModeloImpresora modeloImpresora, String correoElectronicoPagoTarjeta, String proveedor) {
        this.id = id;
        this.empresa = empresa;
        this.domicilio = domicilio;
        this.numero = numero;
        this.colonia = colonia;
        this.rfc = rfc;
        this.cp = cp;
        this.endPointServicioPrincipal = endPointServicioPrincipal;
        this.tipovendedor = tipovendedor;
        this.caja = caja;
        this.almacenMercancia = almacenMercancia;
        this.almacenMateriaPrima = almacenMateriaPrima;
        this.impresora = impresora;
        this.endPointServicioPagoTerminal = endPointServicioPagoTerminal;
        this.terminal = terminal;
        this.numeroSeriePinpad = numeroSeriePinpad;
        this.direccionBTPinpad = direccionBTPinpad;
        this.modeloImpresora = modeloImpresora;
        this.correoElectronicoPagoTarjeta = correoElectronicoPagoTarjeta;
        this.proveedor = proveedor;
    }

    public Configuracion() {
    }


//    public Configuracion(Configuracion config) {
//        this.setId( config.getId() );
//        this.setEmpresa( config.getEmpresa() );
//        this.setDomicilio( config.getDomicilio() );
//        this.setNumero( config.getNumero() );
//        this.setColonia( config.getColonia() );
//        this.setRfc( config.getRfc() );
//        this.setCp( config.getCp() );
//        this.setEndPointServicioPrincipal( config.getEndPointServicioPrincipal() );
//        this.setTipovendedor( config.getTipovendedor() );
//        this.setTipovendedor( config.getTipovendedor() );
//        this.setModalidad( config.getModalidad());
//        this.setImpresora(config.getImpresora());
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipovendedor() {
        return tipovendedor;
    }

    public void setTipovendedor(int tipovendedor) {
        this.tipovendedor = tipovendedor;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getEndPointServicioPrincipal() {
        return endPointServicioPrincipal;
    }

    public void setEndPointServicioPrincipal(String endPointServicioPrincipal) {
        this.endPointServicioPrincipal = endPointServicioPrincipal;
    }

    public String getImpresora() {
        return impresora;
    }

    public void setImpresora(String impresora) {
        this.impresora = impresora;
    }

    public String getEndPointServicioPagoTerminal() {
        return endPointServicioPagoTerminal;
    }

    public void setEndPointServicioPagoTerminal(String endPointServicioPagoTerminal) {
        this.endPointServicioPagoTerminal = endPointServicioPagoTerminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getNumeroSeriePinpad() {
        return numeroSeriePinpad;
    }

    public void setNumeroSeriePinpad(String numeroSeriePinpad) {
        this.numeroSeriePinpad = numeroSeriePinpad;
    }

    public String getDireccionBTPinpad() {
        return direccionBTPinpad;
    }

    public void setDireccionBTPinpad(String direccionBTPinpad) {
        this.direccionBTPinpad = direccionBTPinpad;
    }

    public EnumModeloImpresora getModeloImpresora() {
        return modeloImpresora;
    }

    public void setModeloImpresora(EnumModeloImpresora modeloImpresora) {
        this.modeloImpresora = modeloImpresora;
    }

    public String getCorreoElectronicoPagoTarjeta() {
        return correoElectronicoPagoTarjeta;
    }

    public void setCorreoElectronicoPagoTarjeta(String correoElectronicoPagoTarjeta) {
        this.correoElectronicoPagoTarjeta = correoElectronicoPagoTarjeta;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getAlmacenMercancia() {
        return almacenMercancia;
    }

    public void setAlmacenMercancia(String almacenMercancia) {
        this.almacenMercancia = almacenMercancia;
    }

    public String getAlmacenMateriaPrima() {
        return almacenMateriaPrima;
    }

    public void setAlmacenMateriaPrima(String almacenMateriaPrima) {
        this.almacenMateriaPrima = almacenMateriaPrima;
    }

    //    public String getEndPoint3() {
//        return endPoint3;
//    }
//
//    public void setEndPoint3(String endPoint3) {
//        this.endPoint3 = endPoint3;
//    }
}
