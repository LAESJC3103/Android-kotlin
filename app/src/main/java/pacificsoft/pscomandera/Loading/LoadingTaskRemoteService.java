package pacificsoft.pscomandera.Loading;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.ConfiguracionActivity;
import pacificsoft.pscomandera.LoginActivity;
import pacificsoft.pscomandera.R;
import pacificsoft.pscomandera.serviciosweb.AMTBasicHttpBinding_IService;


/**
 * Created by desarrollo ORM on 2/14/2018.
 */

public class LoadingTaskRemoteService extends AsyncTask<String, Void, DatosRegresados> {
    String res = "";
    Activity act = null;
    int peticion = -1;
    AlertDialog pd;
    TextView txtauxiliar = null;
    AMTBasicHttpBinding_IService service = null;
    String msjProgressDialog = "";

    private LoadingTaskRemoteServiceListener mListener;


    /*OPCIONES PETICIONES */
    final public static int VALIDARCON = 0;
    final public static int LOGIN = 1;
    final public static int BUSCARART = 2;
    final public static int BUSCARUSU = 3;
    final public static int CAJAS = 4;
    final public static int ALMACENES = 5;
    final public static int VALCAJA = 6;
    final public static  int VALIDARCON2 = 7;
    final public static int TICKET_CORREO = 8;
    final public static int GEOALERTA = 9;
    final public static int GEOALERTAVENTA = 10;

    /* FIN DE OPCIONES */

    DialogFragment df;

    public interface LoadingTaskRemoteServiceListener {
        void OnLoadingTasnkFinish(DatosRegresados datosRegresados, int peticion);
    }

    public TextView getTxtauxiliar() {
        return txtauxiliar;
    }

    public void setTxtauxiliar(TextView txtauxiliar) {
        this.txtauxiliar = txtauxiliar;
    }

    public DialogFragment getDf() {
        return df;
    }

    public void setDf(DialogFragment df) {
        this.df = df;
    }

    public LoadingTaskRemoteService(AMTBasicHttpBinding_IService Parservice, Activity act, int peticion, AlertDialog parPD, String msjProgressDialog, LoadingTaskRemoteServiceListener listener) {
        this.act = act;
        this.peticion = peticion;
        this.pd = parPD;
        this.service = Parservice;
        this.msjProgressDialog = msjProgressDialog;
        this.mListener = listener;
    }

    public void onPreExecute() {
        AlertDialog.Builder builder;
        Context mContext = act;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.progress_dialog_custom,
                (ViewGroup) act.findViewById(R.id.lyProgressDialogCustom));

        TextView text = (TextView) layout.findViewById(R.id.txtProgressDialogCustom);
        text.setText(msjProgressDialog);

        builder = new AlertDialog.Builder(mContext);
        builder.setView(layout);
        builder.setCancelable(false);
        pd = builder.create();
        pd.show();

    }


    @Override
    protected DatosRegresados doInBackground(String... strings) {

        DatosRegresados datosRegresados = new DatosRegresados();
        datosRegresados.setError(false);

        try {
            switch (peticion) {
                case VALIDARCON:
                    res = service.Validar();
                    break;
                case VALIDARCON2:
                    res = service.GuardaInvalida( "" );
                    if (res != "") {
                        res = "Conectado";

                    } else {
                        res = "No conectado";
                    }
                    break;

                case CAJAS:
                    res = service.Cajas();
                    break;
                case ALMACENES:
                    res = service.Almacenes();
                    break;
            }

            datosRegresados.setDatosRegresadosString(res);
        } catch (Exception ex) {
            datosRegresados.setError(true);
            datosRegresados.setDatosRegresados(ex);

            // MensajeClass.toast( act.getApplicationContext(), "Error al realizar una peticion " + ex.getMessage() ).show();
        }
        return datosRegresados;
    }

    @Override
    protected void onPostExecute(DatosRegresados datosRegresados) {
        super.onPostExecute( datosRegresados );
        pd.dismiss();
        mListener.OnLoadingTasnkFinish(datosRegresados,peticion);
    }
}

