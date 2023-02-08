package pacificsoft.pscomandera.Modelo;

import java.math.BigDecimal;

public class Movimiento {
    private Integer TIPO_MOVTC;
    private String TIPO_TARJETA;
    private Integer TIPO_ENTRADA;
    private Integer CODIGO_CAJA;
    private String NUM_TARJETA;
    private String REFER_MOVT;
    private String FECHAHORA_TC;
    private String FECHA_MOVTC;
    private String HORA_MOVTC;
    private String AUTORIZ_MOVTC;
    private String REFER_PS;
    private String IMPORTE_MOVTC;
    private String CARGO_MOVTC;
    private Integer RENGLON_MOVCAJ;
    private String ID_CLIENTE;
    private Integer COD_TCPROMO;
    private String APROBACION_MOV;
    private BigDecimal PROPINA_MOVTC;
    private String NUM_TERMINAL;
    private String ID_PROVEEDOR;
    private String NUM_TRANSAC;

    public Movimiento(Integer TIPO_MOVTC, String TIPO_TARJETA, Integer TIPO_ENTRADA, Integer CODIGO_CAJA, String NUM_TARJETA, String REFER_MOVT, String FECHAHORA_TC, String FECHA_MOVTC, String HORA_MOVTC, String AUTORIZ_MOVTC, String REFER_PS, String IMPORTE_MOVTC, String CARGO_MOVTC, Integer RENGLON_MOVCAJ, String ID_CLIENTE, Integer COD_TCPROMO, String APROBACION_MOV, BigDecimal PROPINA_MOVTC, String NUM_TERMINAL, String ID_PROVEEDOR, String NUM_TRANSAC) {
        this.TIPO_MOVTC = TIPO_MOVTC;
        this.TIPO_TARJETA = TIPO_TARJETA;
        this.TIPO_ENTRADA = TIPO_ENTRADA;
        this.CODIGO_CAJA = CODIGO_CAJA;
        this.NUM_TARJETA = NUM_TARJETA;
        this.REFER_MOVT = REFER_MOVT;
        this.FECHAHORA_TC = FECHAHORA_TC;
        this.FECHA_MOVTC = FECHA_MOVTC;
        this.HORA_MOVTC = HORA_MOVTC;
        this.AUTORIZ_MOVTC = AUTORIZ_MOVTC;
        this.REFER_PS = REFER_PS;
        this.IMPORTE_MOVTC = IMPORTE_MOVTC;
        this.CARGO_MOVTC = CARGO_MOVTC;
        this.RENGLON_MOVCAJ = RENGLON_MOVCAJ;
        this.ID_CLIENTE = ID_CLIENTE;
        this.COD_TCPROMO = COD_TCPROMO;
        this.APROBACION_MOV = APROBACION_MOV;
        this.PROPINA_MOVTC = PROPINA_MOVTC;
        this.NUM_TERMINAL = NUM_TERMINAL;
        this.ID_PROVEEDOR = ID_PROVEEDOR;
        this.NUM_TRANSAC = NUM_TRANSAC;
    }

    public Integer getTIPO_MOVTC() {
        return TIPO_MOVTC;
    }

    public void setTIPO_MOVTC(Integer TIPO_MOVTC) {
        this.TIPO_MOVTC = TIPO_MOVTC;
    }

    public String getTIPO_TARJETA() {
        return TIPO_TARJETA;
    }

    public void setTIPO_TARJETA(String TIPO_TARJETA) {
        this.TIPO_TARJETA = TIPO_TARJETA;
    }

    public Integer getTIPO_ENTRADA() {
        return TIPO_ENTRADA;
    }

    public void setTIPO_ENTRADA(Integer TIPO_ENTRADA) {
        this.TIPO_ENTRADA = TIPO_ENTRADA;
    }

    public Integer getCODIGO_CAJA() {
        return CODIGO_CAJA;
    }

    public void setCODIGO_CAJA(Integer CODIGO_CAJA) {
        this.CODIGO_CAJA = CODIGO_CAJA;
    }

    public String getNUM_TARJETA() {
        return NUM_TARJETA;
    }

    public void setNUM_TARJETA(String NUM_TARJETA) {
        this.NUM_TARJETA = NUM_TARJETA;
    }

    public String getREFER_MOVT() {
        return REFER_MOVT;
    }

    public void setREFER_MOVT(String REFER_MOVT) {
        this.REFER_MOVT = REFER_MOVT;
    }

    public String getFECHAHORA_TC() {
        return FECHAHORA_TC;
    }

    public void setFECHAHORA_TC(String FECHAHORA_TC) {
        this.FECHAHORA_TC = FECHAHORA_TC;
    }

    public String getFECHA_MOVTC() {
        return FECHA_MOVTC;
    }

    public void setFECHA_MOVTC(String FECHA_MOVTC) {
        this.FECHA_MOVTC = FECHA_MOVTC;
    }

    public String getHORA_MOVTC() {
        return HORA_MOVTC;
    }

    public void setHORA_MOVTC(String HORA_MOVTC) {
        this.HORA_MOVTC = HORA_MOVTC;
    }

    public String getAUTORIZ_MOVTC() {
        return AUTORIZ_MOVTC;
    }

    public void setAUTORIZ_MOVTC(String AUTORIZ_MOVTC) {
        this.AUTORIZ_MOVTC = AUTORIZ_MOVTC;
    }

    public String getREFER_PS() {
        return REFER_PS;
    }

    public void setREFER_PS(String REFER_PS) {
        this.REFER_PS = REFER_PS;
    }

    public String getIMPORTE_MOVTC() {
        return IMPORTE_MOVTC;
    }

    public void setIMPORTE_MOVTC(String IMPORTE_MOVTC) {
        this.IMPORTE_MOVTC = IMPORTE_MOVTC;
    }

    public String getCARGO_MOVTC() {
        return CARGO_MOVTC;
    }

    public void setCARGO_MOVTC(String CARGO_MOVTC) {
        this.CARGO_MOVTC = CARGO_MOVTC;
    }

    public Integer getRENGLON_MOVCAJ() {
        return RENGLON_MOVCAJ;
    }

    public void setRENGLON_MOVCAJ(Integer RENGLON_MOVCAJ) {
        this.RENGLON_MOVCAJ = RENGLON_MOVCAJ;
    }

    public String getID_CLIENTE() {
        return ID_CLIENTE;
    }

    public void setID_CLIENTE(String ID_CLIENTE) {
        this.ID_CLIENTE = ID_CLIENTE;
    }

    public Integer getCOD_TCPROMO() {
        return COD_TCPROMO;
    }

    public void setCOD_TCPROMO(Integer COD_TCPROMO) {
        this.COD_TCPROMO = COD_TCPROMO;
    }

    public String getAPROBACION_MOV() {
        return APROBACION_MOV;
    }

    public void setAPROBACION_MOV(String APROBACION_MOV) {
        this.APROBACION_MOV = APROBACION_MOV;
    }

    public BigDecimal getPROPINA_MOVTC() {
        return PROPINA_MOVTC;
    }

    public void setPROPINA_MOVTC(BigDecimal PROPINA_MOVTC) {
        this.PROPINA_MOVTC = PROPINA_MOVTC;
    }

    public String getNUM_TERMINAL() {
        return NUM_TERMINAL;
    }

    public void setNUM_TERMINAL(String NUM_TERMINAL) {
        this.NUM_TERMINAL = NUM_TERMINAL;
    }

    public String getID_PROVEEDOR() {
        return ID_PROVEEDOR;
    }

    public void setID_PROVEEDOR(String ID_PROVEEDOR) {
        this.ID_PROVEEDOR = ID_PROVEEDOR;
    }

    public String getNUM_TRANSAC() {
        return NUM_TRANSAC;
    }

    public void setNUM_TRANSAC(String NUM_TRANSAC) {
        this.NUM_TRANSAC = NUM_TRANSAC;
    }
}
