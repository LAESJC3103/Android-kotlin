package pacificsoft.pscomandera.Modelo;

public enum TipoModal {
    // Ejemplo aprenderaprogramar.com

// Clase que contiene los tipos de madera que usa la empresa, su color y su peso específico en kg/m3

    IMPRESORA_INICIAL("impresoraInicial"),
    IMPRESORA("impresora"),
    IMPRESORA_INICIAL_NO_ENCONTRADA("impresoraNoEncontradaInicial"),
    IMPRESORA_NO_ENCONTRADA("impresoraNoEncontrada"),
    IMPRESORA_NO_ENCONTRADA_INICIAL_CERRAR_SESION("impresoraNoEncontradaCerrarSesion"),
    IMPRESORA_ENCONTRADA("impresoraEncontrada"),
    IMPRESORA_INICIAL_ENCONTRADA("impresoraInicialEncontrada"),
    TERMINAL_INICIAL("terminalInicial"),
    TERMINAL("terminal"),
    TERMINAL_FINALIZAR_VENTA("terminalFinalizarVenta"),
    TERMINAL_NO_ENCONTRADA_FINALIZAR_VENTA("terminalNoEncontradaFinalizarVenta"),
    TERMINAL_NO_ENCONTRADA_INICIAL("terminalNoEncontradaInicial"),
    TERMINAL_NO_ENCONTRADA_INICIAL_CERRAR_SESION("terminalNoEncontradaInicialCerrarSesion"),
    TERMINAL_NO_ENCONTRADA("terminalNoEncontrada"),
    TERMINAL_INICIAL_ENCONTRADA("terminalInicialEncontrada"),
    TERMINAL_ENCONTRADA("terminalEncontrada"),
    CERRAR_SESION_DISPOSITIVOS("cerrarSesionDispositivos"),
    CERRAR_SESION("cerrarSesion"),
    CERRAR_SESION_SIN_GUARDAR("cerrarSesionSinGuardar"),
    ALERTA_PREVENCION("alertaPreveencion"),
    ALERTA_OPERECION_EXITOSA("alertaOperacionExitosa"),
    DISPOSITIVOS_ENCONTRADOS("dispositivosEncontrados"),
    OPERACION_EXITOSA("operacionExitosa"),
    OPERACION_EXITOSA_NOTA_VENTA_GUARDADA("operacionExitosaNotaVentaGuardada"),
    ERROR_OPERACION_ACELERADOR_FILAS_NOTA_VENTA_GUARDAR("errorOperacionAceleradorFilasNotaVentaGuardar"),
    ERROR_OPERACION("errorOperacion"),
    VALIDAR_IMPRESORA_IMPRIMIR("validarImpresoraImprimir"),
    ERROR_VALIDAR_IMPRESORA_IMPRIMIR("errorValidarImprsoraImprimir"),
    NOTA_SURTIDO_GUARDADA("notaSurtidoGuardada"),
    INICIALIZACION_LLAVES_CORRECTA("inicializacionLlavesCorrecta"),
    IMPRIMIR_TICKET("imprimir"),
    IMPRIMIR_COPIA_VOUCHER("imprimirVouvher"),
    PAGO_TARJETA_EXITOSO("pagoTarjetaExitoso"),
    PAGO_TARJETA_ERROR("pagoTarjetaError"),
    ERROR_TRANSACCION("errorTransaccion"),
    SIN_ACCION ("sinAccion"), //Separamos con comas
    TOMAR_MESA("tomarMesaLlamadoAsync"),
    MESA_OCUPADA("mesaOcupada");




    //Campos tipo constante

    private final String clave; //Color de la madera




    /**

     * Constructor. Al asignarle uno de los valores posibles a una variable del tipo enumerado el constructor asigna

     automáticamente valores de los campos

     */

    TipoModal (String clave) {

        this.clave = clave;

    } //Cierre del constructor


    public String getClave() {
        return clave;
    }
}
