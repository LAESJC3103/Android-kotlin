package pacificsoft.pscomandera.LocalManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import pacificsoft.pscomandera.Enum.EnumColumnasTablaEmpresa;
import pacificsoft.pscomandera.Enum.EnumTablas;

/**
 * Created by desarrollo on 12/20/2017.
 */

public class DbLocalManager extends SQLiteOpenHelper {

    public final static String TABLA_EMPRESA = "Empresa";

    String campos = EnumColumnasTablaEmpresa.TABLA_EMPRESA_ID.getClaveColumna()+","
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PRINCIPAL.getClaveColumna()+"," /*0*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_RFC.getClaveColumna()+"," /*image_nav_header_default_comanda*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_NOMBRE_EMPRESA.getClaveColumna()+"," /*2*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_DOMICILIO.getClaveColumna()+"," /*3*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO.getClaveColumna()+"," /*4*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_COLONIA.getClaveColumna()+"," /*5*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_CP.getClaveColumna()+"," /*6*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_TIPO_VENDEDOR.getClaveColumna()+"," /*7*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_CAJA.getClaveColumna()+"," /*8*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MERCANCIA.getClaveColumna()+"," /*9*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MATERIA_PRIMA.getClaveColumna()+"," /*10*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_IMPRESORA.getClaveColumna()+"," /*11*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_MODELO_IMPRESORA.getClaveColumna()+"," /*12*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PAGO_TARJETA.getClaveColumna()+"," /*13*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_TERMINAL.getClaveColumna()+"," /*14*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO_SERIE_PINPAD.getClaveColumna()+"," /*15*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_DIRECCION_BT_PINPAD.getClaveColumna()+"," /*16*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_PROVEEDOR_PAGO_TARJETA.getClaveColumna()+"," /*17*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_CORREO_DEFAULT_PAGO_TARJETA.getClaveColumna(); /*18*/

    String setcampostablaempresa = EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PRINCIPAL.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_RFC.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_NOMBRE_EMPRESA.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_DOMICILIO.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_COLONIA.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_CP.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_TIPO_VENDEDOR.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_CAJA.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MERCANCIA.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MATERIA_PRIMA.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_IMPRESORA.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_MODELO_IMPRESORA.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PAGO_TARJETA.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_TERMINAL.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO_SERIE_PINPAD.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_DIRECCION_BT_PINPAD.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_PROVEEDOR_PAGO_TARJETA.getClaveColumna()+"=?," +
            EnumColumnasTablaEmpresa.TABLA_EMPRESA_CORREO_DEFAULT_PAGO_TARJETA.getClaveColumna()+"=?";


    private EnumTablas TABLA = EnumTablas.TABLAS_EMPTY;

    String empresa = "CREATE TABLE  "+ EnumTablas.TABLA_CONFIGURACION_EMPRESA.getClaveTabla() +"("
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ID.getClaveColumna()+" integer,"
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PRINCIPAL.getClaveColumna()+" Text," /*0*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_RFC.getClaveColumna()+" Text," /*image_nav_header_default_comanda*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_NOMBRE_EMPRESA.getClaveColumna()+" TEXT," /*2*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_DOMICILIO.getClaveColumna()+" TEXT," /*3*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO.getClaveColumna()+" TEXT," /*4*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_COLONIA.getClaveColumna()+" TEXT," /*5*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_CP.getClaveColumna()+" TEXT," /*6*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_TIPO_VENDEDOR.getClaveColumna()+" TEXT," /*7*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_CAJA.getClaveColumna()+" TEXT, " /*8*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MERCANCIA.getClaveColumna()+" TEXT," /*9*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MATERIA_PRIMA.getClaveColumna()+" TEXT," /*10*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_IMPRESORA.getClaveColumna()+" TEXT," /*11*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_MODELO_IMPRESORA.getClaveColumna()+" Numeric," /*12*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PAGO_TARJETA.getClaveColumna()+" TEXT," /*13*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_TERMINAL.getClaveColumna()+" TEXT," /*14*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO_SERIE_PINPAD.getClaveColumna()+" TEXT," /*15*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_DIRECCION_BT_PINPAD.getClaveColumna()+" TEXT," /*16*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_PROVEEDOR_PAGO_TARJETA.getClaveColumna()+" TEXT," /*17*/
            +EnumColumnasTablaEmpresa.TABLA_EMPRESA_CORREO_DEFAULT_PAGO_TARJETA.getClaveColumna()+" TEXT)";/*18*/


    public DbLocalManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super( context, name, factory, version );

    }

    private String createStringUpdate(List<String> param, String[] listaSetCampos) {
        String strSetQueryUpdate = "";

        for (int x = 0; x < listaSetCampos.length; x++) {
            String valParam = param.get( x );
            String valsetCampo;
            if (valParam != null) {
                valsetCampo = listaSetCampos[x].replace( "?", "'" + valParam + "'" );
                if (x != listaSetCampos.length - 1) {
                    valsetCampo += ",";
                }
                listaSetCampos[x] = valsetCampo;
                strSetQueryUpdate += valsetCampo;
            }
        }
        return strSetQueryUpdate;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(empresa);
    }


    public void Save(List<String> param, int ID, EnumTablas tabla) throws Exception{
        String[] listaSetCampos = null;
        setNAME_TABLA( tabla );
        if (getData().getCount() > 0) {
            switch (tabla) {
                case TABLA_CONFIGURACION_EMPRESA:
                    listaSetCampos = setcampostablaempresa.split( "," );
                    break;
            }
            String strSQLUpdate = " UPDATE " + tabla.getClaveTabla() + " SET " + createStringUpdate( param, listaSetCampos ) + " WHERE ID=" + ID;
            getWritableDatabase().execSQL( strSQLUpdate );
        } else {

            String strCampos = null;
            switch (tabla) {
                case TABLA_CONFIGURACION_EMPRESA:
                    strCampos = campos;
                    break;
            }
            String params = "";
            for (int k = 0; k < param.size(); k++) {
                String strvalor = "'" + param.get( k )+"'";
                params += strvalor + ",";
            }
            String strSQL = " INSERT INTO " + tabla.getClaveTabla() + "(" + strCampos + ")VALUES('1'," + params.substring( 0, params.length() - 1 ) + ")";
            getWritableDatabase().execSQL( strSQL );
        }
    }


    public EnumTablas getNAME_TABLA() {
        return TABLA;
    }

    public void setNAME_TABLA(EnumTablas TABLA) {
        this.TABLA = TABLA;
    }



    public Cursor getData() {
        String strSQL = "SELECT ";
        switch (getNAME_TABLA()) {
            case TABLA_CONFIGURACION_EMPRESA:
                strSQL += campos + " FROM  " + getNAME_TABLA().getClaveTabla();
                break;
        }
        return getWritableDatabase().rawQuery( strSQL, null );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            while (oldVersion < newVersion) {
                switch (oldVersion) {

                    case 1:
                        //En caso de ser la segunda version de base de datos
                        break;

                    default:
                        Log.d("Actualizacion BD", "Database upgraded to version " + newVersion);
                        break;

                }
                oldVersion++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
