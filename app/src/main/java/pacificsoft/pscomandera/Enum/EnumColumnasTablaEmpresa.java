package pacificsoft.pscomandera.Enum;

public enum EnumColumnasTablaEmpresa {
    TABLA_EMPRESA_ID("ID"),
    TABLA_EMPRESA_ENDPOINT_SERVICIO_PRINCIPAL("endpointServicioPrincipal"),
    TABLA_EMPRESA_RFC("rfc"),
    TABLA_EMPRESA_NOMBRE_EMPRESA("nombreEmpresa"),
    TABLA_EMPRESA_DOMICILIO("domicilio"),
    TABLA_EMPRESA_NUMERO("numero"),
    TABLA_EMPRESA_COLONIA("colonia"),
    TABLA_EMPRESA_CP("cp"),
    TABLA_EMPRESA_TIPO_VENDEDOR("tipoVendedor"),
    TABLA_EMPRESA_CAJA("caja"),
    TABLA_EMPRESA_ALMACEN_MERCANCIA("almacenMercancia"),
    TABLA_EMPRESA_ALMACEN_MATERIA_PRIMA("almacenMateriaPrima"),
    TABLA_EMPRESA_IMPRESORA("impresora"),
    TABLA_EMPRESA_MODELO_IMPRESORA("modeloImpresora"),
    TABLA_EMPRESA_ENDPOINT_SERVICIO_PAGO_TARJETA("endpointServicioPagoTarjeta"),
    TABLA_EMPRESA_TERMINAL("terminal"),
    TABLA_EMPRESA_NUMERO_SERIE_PINPAD("numeroSeriePinpad"),
    TABLA_EMPRESA_DIRECCION_BT_PINPAD("direccionBTPinpad"),
    TABLA_EMPRESA_PROVEEDOR_PAGO_TARJETA("proveedor"),
    TABLA_EMPRESA_CORREO_DEFAULT_PAGO_TARJETA("correoElectronicoPagoTarjeta");

    String claveColumna;

    EnumColumnasTablaEmpresa(String claveColumna) {
        this.claveColumna = claveColumna;
    }

    public String getClaveColumna() {
        return claveColumna;
    }
}
