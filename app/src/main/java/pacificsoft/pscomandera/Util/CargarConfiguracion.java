package pacificsoft.pscomandera.Util;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import pacificsoft.pscomandera.Enum.Enum;
import pacificsoft.pscomandera.Enum.EnumColumnasTablaEmpresa;
import pacificsoft.pscomandera.Enum.EnumTablas;
import pacificsoft.pscomandera.Impresion.Metodos;
import pacificsoft.pscomandera.LocalManager.DbLocalManager;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.R;

public class CargarConfiguracion extends AsyncTask<Object,Object, DatosRegresados> {

    public interface CargaConfiguracionListener {
        void cargaConfiguracionCompleta(DatosRegresados datosregresados);
    }

    private CargaConfiguracionListener listener;

    private Context context;
    private DbLocalManager dbLocalManager;

    public CargarConfiguracion(CargaConfiguracionListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected DatosRegresados doInBackground(Object... objects) {

        DatosRegresados datosRegresados = new DatosRegresados();
        try{
            dbLocalManager = new DbLocalManager( context, context.getString(R.string.database_name), null, new Metodos().version );
            datosRegresados = fillConfigData();
        }catch (Exception ex){
            datosRegresados.setError(true);
            datosRegresados.setDatosRegresados(ex);
        }
        return datosRegresados;
    }

    @Override
    protected void onPostExecute(DatosRegresados datosRegresados) {
        listener.cargaConfiguracionCompleta(datosRegresados);
    }

    public DatosRegresados fillConfigData() {

        DatosRegresados datosRegresados = new DatosRegresados();
        datosRegresados.setError(false);

        Configuracion configuracion;
        try {
            Enum enumList = new Enum();
            dbLocalManager.setNAME_TABLA( EnumTablas.TABLA_CONFIGURACION_EMPRESA);
            Cursor c = dbLocalManager.getData();
            if (c.getCount() > 0 && c.moveToFirst()) {
                configuracion = new Configuracion();
                configuracion.setId(c.getInt( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_ID.getClaveColumna()) ));
                configuracion.setEndPointServicioPrincipal(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PRINCIPAL.getClaveColumna()) ));
                configuracion.setRfc(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_RFC.getClaveColumna()) ));
                configuracion.setEmpresa(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_NOMBRE_EMPRESA.getClaveColumna()) ));
                configuracion.setDomicilio(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_DOMICILIO.getClaveColumna()) ));
                configuracion.setNumero(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO.getClaveColumna()) ));
                configuracion.setColonia(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_COLONIA.getClaveColumna()) ));
                configuracion.setCp(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_CP.getClaveColumna()) ));
                configuracion.setTipovendedor(c.getInt( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_TIPO_VENDEDOR.getClaveColumna()) ));
                configuracion.setCaja(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_CAJA.getClaveColumna()) ));
                configuracion.setAlmacenMateriaPrima(c.getString(c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MATERIA_PRIMA.getClaveColumna())));
                configuracion.setAlmacenMercancia(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_ALMACEN_MERCANCIA.getClaveColumna()) ));
                configuracion.setImpresora(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_IMPRESORA.getClaveColumna()) ));
                configuracion.setModeloImpresora(enumList.getModeloImpresoraById(c.getInt( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_MODELO_IMPRESORA.getClaveColumna()))));
                configuracion.setEndPointServicioPagoTerminal(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_ENDPOINT_SERVICIO_PAGO_TARJETA.getClaveColumna()) ));
                configuracion.setTerminal(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_TERMINAL.getClaveColumna()) ));
                configuracion.setNumeroSeriePinpad(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_NUMERO_SERIE_PINPAD.getClaveColumna()) ));
                configuracion.setDireccionBTPinpad(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_DIRECCION_BT_PINPAD.getClaveColumna()) ));
                configuracion.setProveedor(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_PROVEEDOR_PAGO_TARJETA.getClaveColumna()) ));
                configuracion.setCorreoElectronicoPagoTarjeta(c.getString( c.getColumnIndex(EnumColumnasTablaEmpresa.TABLA_EMPRESA_CORREO_DEFAULT_PAGO_TARJETA.getClaveColumna()) ));
                datosRegresados.setDatosRegresados(configuracion);
            }

            if(datosRegresados==null){
                datosRegresados.setDatosRegresados(null);
            }

        }catch (Exception ex){
            datosRegresados.setError(true);
            datosRegresados.setDatosRegresados(ex);
        }
        return datosRegresados;
    }
}
