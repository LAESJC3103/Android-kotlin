package pacificsoft.pscomandera.Enum;

public class StaticVariables {

  public class REQUEST{
    public static final int OTHER_ACTIVITY = 0;
    public static final int SPLASH_ACTIVITY = 1;
    public static final int LOGIN_ACTIVITY = 2;
    public static final int AREA_ACTIVITY = 3;
    public static final int MESA_ACTIVITY = 4;
    public static final int CARTA_ACTIVITY = 5;
    public static final int GRUPO_ACTIVITY = 6;
    public static final int COMANDA_ACTIVITY = 7;
    public static final int ACERCA_DE_ACTIVITY = 8;
    public static final int MAS_APLICACIONES_ACTIVITY = 9;
    public static final int DETALLE_COMANDA_EDITAR_ACTIVITY = 10;
    public static final int DETALLE_COMANDA_AGREGAR_ACTIVITY = 11;
    public static final int CONFIGURACION_ACTIVITY = 12;
    public static final int MODIFICADOR_ACTIVITY = 13;

  }

  public class RESULT_ACTIVITY {
    public static final int SIN_ACCION = 0;
    public static final int CERRAR_SESION = 1;
    public static final int GUARDAR_CAMBIOS = 2;
    public static final int ERROR = 3;
    public static final int SE_AGREGO_CORRECTAMENTE = 4;
    public static final int SE_EDITO_CORRECTAMENTE = 5;
    public static final int CERRAR_SESION_PARENT = 6;
    public static final int OPERACION_CORRECTA = 7;
    public static final int COMANDA_ENVIADA = 8;
  }

  public class PETICION_COMANDA_ASYNC{
    public static final int SIN_PROGRESS_DIALOG = 0;
    public static final int AGREGAR_ARTICULO = 1;
    public static final int EDITAR_COMANDA =2;
    public static final int OBTENER_LISTA_COMANDAS = 3;
    public static final int OBTENER_COMANDA = 4;
    public static final int ELIMINAR_ARTICULOS = 5;
    public static final int ELIMINAR_COMANDA = 6;
    public static final int OBTENER_PRECIO_ARTICULO = 7;
    public static final int LIMPIAR_COMANDAS = 8;
  }


}
