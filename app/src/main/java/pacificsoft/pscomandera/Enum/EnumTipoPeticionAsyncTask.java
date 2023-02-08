package pacificsoft.pscomandera.Enum;

public enum EnumTipoPeticionAsyncTask {
    VALIDARSERVICIOPRINCIPAL(0,"Valida servicio principal"),
    LOGIN_COMANDERA(1,"Servicio para hacer login"),
    VALIDARSERVICIOTERMINAL(2,"Valida el servicio para pago con tarjeta"),
    DATOSTERMINAL(3,"Obtiene los datos de la terminal ingresada"),
    GUARDAMOVIMIENTOS(4,"Obtiene los datos de la terminal ingresada"),
    AREASYMESAS(5,"Obtiene las areas y sus respectivas mesas del restaurant"),
    CARTAS(6,"Obtiene las cartas y sus respectivos grupos"),
    CAJAS(7,"Obtiene las cajas de almacen"),
    ALMACEN_MERCANCIA(8,"Obtiene los almacenes tipo mercancia"),
    ALMACEN_MATERIA_PRIMA(9,"Obtiene los almacenes tipo materia prima"),
    PRECIO_ARTICULO(10,"Obtiene el precio del articulo especificado"),
    ORDEN_COMANDA(11,"Guarda la comanda"),
    CERRAR_CAJA(12,"Libera la caja en la que se estaba ocupando"),
    TOMAR_MESA(13,"Toma la mesa"),
    OBTENER_NOTAS(14,"Obtiene las notas predefinidas"),
    OBTENER_MODIFICADORES(15,"Obtiene los modificadores del articulo seleccionado");
//    ,
//    OBTENER_CUENTAS_MESA(16,"Obtiene las cuentas creadas en la mesa especificada");

    int id;
    String descripcion;
    EnumTipoPeticionAsyncTask(int id,String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }
}
