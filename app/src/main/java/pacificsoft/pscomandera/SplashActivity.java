package pacificsoft.pscomandera;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import pacificsoft.pscomandera.Enum.EnumTablas;
import pacificsoft.pscomandera.Impresion.Metodos;
import pacificsoft.pscomandera.Loading.LoadingTaskRemoteService;
import pacificsoft.pscomandera.LocalManager.DbLocalManager;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Util.ConvertXmlToJSONManager;
import pacificsoft.pscomandera.serviciosweb.AMTBasicHttpBinding_IService;

public class SplashActivity extends AppCompatActivity implements LoadingTaskRemoteService.LoadingTaskRemoteServiceListener {

    Configuracion config = null;
    DbLocalManager lmanager = null;
    AMTBasicHttpBinding_IService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onPostResume() {
        MyTimerTask myTask = new MyTimerTask(this);
        Timer myTimer = new Timer();
        myTimer.schedule(myTask,1000);
        super.onPostResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void OnLoadingTasnkFinish(DatosRegresados datosRegresados, int peticion) {
        if(!datosRegresados.getError()){
            switch (peticion){
                case LoadingTaskRemoteService.LOGIN:
                    break;
                case LoadingTaskRemoteService.VALIDARCON:{
                    String xmlData = datosRegresados.getDatosRegresadosString();

                    if(xmlData == null || xmlData.equals("")){
                        Intent ix = new Intent( SplashActivity.this, ConfiguracionActivity.class );
                        startActivity( ix );
                        finish();
                    }else{
                        String codigo = ConvertXmlToJSONManager.Convertidor( xmlData, "Codigo", "Respuesta" ).get(0);

                        if(!codigo.equals("0")){
                            Intent ix = new Intent( SplashActivity.this, ConfiguracionActivity.class );
                            startActivity( ix );
                            finish();
                        }
                    }

                }
            }
        }else{
            switch (peticion){
                case LoadingTaskRemoteService.VALIDARCON:{
                    Intent ix = new Intent( SplashActivity.this, ConfiguracionActivity.class );
                    startActivity( ix );
                    finish();
                    break;
                }
            }
        }
    }

    class MyTimerTask extends TimerTask {
        Context context;
        MyTimerTask(Context context){
            this.context = context;
        }
        public void run(){
            Intent intentLogin = new Intent(context,LoginActivity.class);
            startActivity(intentLogin);
            finish();
//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
    }

    public Configuracion getConfigData() {
        lmanager.setNAME_TABLA( EnumTablas.TABLA_CONFIGURACION_EMPRESA );
        Cursor c = lmanager.getData();
        if (c.getCount() > 0 && c.moveToFirst()) {
            config = new Configuracion();
            config.setId(c.getInt( c.getColumnIndex("ID") ));
            config.setEmpresa(c.getString( c.getColumnIndex("empresa") ));
            config.setDomicilio(c.getString( c.getColumnIndex("domicilio")));
            config.setNumero(c.getString( c.getColumnIndex("numero") ));
            config.setColonia(c.getString( c.getColumnIndex("colonia") ));
            config.setRfc(c.getString( c.getColumnIndex("rfc") ));
            config.setCp(c.getString( c.getColumnIndex("cp")));
            //config.setEndPoint(c.getString( c.getColumnIndex("endpoint")));
            config.setTipovendedor(c.getInt( c.getColumnIndex("tipovendedor")));
            //config.setEnumModalidad(enumPiso.getModalidadByClave(c.getString( c.getColumnIndex("Modalidad"))));
            config.setImpresora(c.getString(c.getColumnIndex("Impresora")));
            //config.setEndPoint2( c.getString(c.getColumnIndex("endpoint2")));
            //config.setPuntoVenta(c.getString(c.getColumnIndex("puntoVenta")));
            //config.setClaveUsuario(c.getString(c.getColumnIndex("claveUsuario")));
            config.setTerminal(c.getString(c.getColumnIndex("terminal")));
            config.setNumeroSeriePinpad(c.getString(c.getColumnIndex("numeroSeriePinpad")));
            //config.setServerIP(c.getString(c.getColumnIndex("serverIP")));
            //config.setServerPort(c.getString(c.getColumnIndex("serverPort")));
            config.setDireccionBTPinpad(c.getString(c.getColumnIndex("direccionBTPinpad")));
            config.setCorreoElectronicoPagoTarjeta(c.getString(c.getColumnIndex("correoElectronicoPagoTarjeta")));
            config.setProveedor(c.getString(c.getColumnIndex("proveedor")));
            //config.setEnumMarcaImpresora(enumCoreImpresion.getMarcaImpresoraById(c.getInt( c.getColumnIndex("marcaImpresora"))));
            //config.setEnumPapelImpresora(enumCoreImpresion.getPapelImpresoraById(c.getInt( c.getColumnIndex("papelImpresora"))));
            //config.setEnumTipoImpresora(EnumTipoImpresora.BLUETOOTH);
            //config.setModeloImpresora(enumCoreImpresion.getModeloImpresoraById(config.getEnumMarcaImpresora(),c.getInt(c.getColumnIndex("modeloImpresora"))));
            //config.setPagoEfectivo(c.getInt(c.getColumnIndex("pagoEfectivo"))==1?true:false);
            //config.setPagoTarjeta(c.getInt(c.getColumnIndex("pagoTarjeta"))==1?true:false);
        }
        return config;
    }

}
