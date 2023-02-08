package pacificsoft.pscomandera.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Enum.StaticVariables;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.Modelo.Comanda;
import pacificsoft.pscomandera.Modelo.ComandaArticulo;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.Cuenta;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.GenericSendData;
import pacificsoft.pscomandera.Modelo.Mesa;
import pacificsoft.pscomandera.Modelo.Platillo;
import pacificsoft.pscomandera.R;

import static android.content.Context.MODE_PRIVATE;

public class ComandaAsync extends AsyncTask<Void,Void, DatosRegresados> {

    private Context context;
    private int peticion;
    AlertDialog pd;
    Object sendData;
    Configuracion configuracion;

    SharedPreferences.Editor editor;
    SharedPreferences mPreferences;

    String stringListaComandas;
    String stringMesaSeleccionada,stringCuentaSeleccionada;

    Mesa mesaSeleccionada ;
    Comanda comanda;
    Cuenta cuentaSeleccionada;
    List<Comanda> lstComandas;

    private ComandaAsyncTaskListener mListener;


    public interface ComandaAsyncTaskListener{
        void OnComandaAsyncFinish(DatosRegresados datosRegresados, int peticion);
    }

    public ComandaAsync(Context context, int peticion, Configuracion configuracion, GenericSendData sendData, ComandaAsyncTaskListener listener) {
        this.context = context;
        this.peticion = peticion;
        this.sendData = sendData;
        this.mListener = listener;
        this.configuracion = configuracion;
        mPreferences = this.context.getSharedPreferences(this.context.getString(R.string.SHPName), MODE_PRIVATE);
        editor = this.context.getSharedPreferences(this.context.getString(R.string.SHPName), MODE_PRIVATE).edit();
        stringListaComandas =  mPreferences.getString(this.context.getString(R.string.SHPTAGComandasPendientes),"");
        stringMesaSeleccionada = mPreferences.getString(this.context.getString(R.string.SHPTAGMesaSeleccionada),"");
        stringCuentaSeleccionada = mPreferences.getString(this.context.getString(R.string.SHPTAGCuentaSeleccionada),"");
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        switch (peticion){
            case StaticVariables.PETICION_COMANDA_ASYNC.SIN_PROGRESS_DIALOG:case StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_ARTICULOS: case StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_COMANDA:
                break;
            default:
                AlertDialog.Builder builder;
                Context mContext = context;
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.progress_dialog_custom,null);

                TextView text = (TextView) layout.findViewById(R.id.txtProgressDialogCustom);

                switch (peticion){

                    case StaticVariables.PETICION_COMANDA_ASYNC.AGREGAR_ARTICULO:
                        text.setText(context.getString(R.string.msj_comanda_agregando_articulo));
                        break;
                    case StaticVariables.PETICION_COMANDA_ASYNC.EDITAR_COMANDA:

                        text.setText(context.getString(R.string.msj_comanda_guardando_cambios));
                        break;
                    case StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_COMANDA:
                        text.setText(R.string.msj_obteniendo_comanda);
                        break;
                    case StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_PRECIO_ARTICULO:
                        text.setText(R.string.msj_obteniendo_precio_articulo);
                        break;
                    case StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_ARTICULOS:
                        text.setText(R.string.msj_comanda_eliminando_articulos);
                        break;
                    case StaticVariables.PETICION_COMANDA_ASYNC.LIMPIAR_COMANDAS:
                        text.setText(R.string.msj_limpiando_limpiar_comandas);
                        break;
                    default:
                        break;
                }
                builder = new AlertDialog.Builder(mContext);
                builder.setView(layout);
                builder.setCancelable(false);
                pd = builder.create();
                pd.show();
                break;
        }
    }

    @Override
    protected DatosRegresados doInBackground(Void... voids) {

        DatosRegresados datosRegresados = new DatosRegresados();
        datosRegresados.setError(false);
        GenericSendData genericSendData;

        int indexCartArticulo = -1;
        ComandaArticulo articuloEditarSeleccionado;

        try{

            obtenerComanda();

            switch (peticion){
                case StaticVariables.PETICION_COMANDA_ASYNC.AGREGAR_ARTICULO:
                    genericSendData = GenericSendData.class.cast(sendData);
                    articuloEditarSeleccionado = ComandaArticulo.class.cast(genericSendData.getObjectSendData());

                    addToCart(articuloEditarSeleccionado);

                    datosRegresados.setError(false);

                    break;

                case StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_PRECIO_ARTICULO:
                    genericSendData = GenericSendData.class.cast(sendData);
                    articuloEditarSeleccionado = ComandaArticulo.class.cast(genericSendData.getObjectSendData());

                    datosRegresados = obtenerPrecio(articuloEditarSeleccionado);

                    if(datosRegresados.getError()){
                        throw new Exception(Exception.class.cast(datosRegresados.getDatosRegresados()));
                    }

                    break;
                case StaticVariables.PETICION_COMANDA_ASYNC.EDITAR_COMANDA:

                    genericSendData = GenericSendData.class.cast(sendData);

                    articuloEditarSeleccionado = ComandaArticulo.class.cast(genericSendData.getObjectSendData());

                    indexCartArticulo = articuloEditarSeleccionado.getPosition();

                    editarComandaArticulo(articuloEditarSeleccionado,indexCartArticulo);

                    datosRegresados.setError(false);

                    break;
                case StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_COMANDA:
                    datosRegresados.setError(false);
                    datosRegresados.setDatosRegresados(comanda);
                    break;
                case StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_ARTICULOS:
                    genericSendData = GenericSendData.class.cast(sendData);
                    List<Integer> selectedItemPositions = (List<Integer>)genericSendData.getListSendData();
                    deleteArticulos(selectedItemPositions);
                    break;
                case StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_COMANDA:
                    deleteComanda(comanda);
                    break;
                case StaticVariables.PETICION_COMANDA_ASYNC.LIMPIAR_COMANDAS:
                    limpiarComandas();
                    break;
            }

        }catch (Exception ex){
            datosRegresados.setError(true);
            datosRegresados.setDatosRegresados(ex);
        }

        return datosRegresados;
    }

    @Override
    protected void onPostExecute(DatosRegresados datosRegresados) {
        super.onPostExecute(datosRegresados);

        switch (peticion){
            case StaticVariables.PETICION_COMANDA_ASYNC.SIN_PROGRESS_DIALOG:case StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_ARTICULOS:case StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_COMANDA:
                break;
            default:
                pd.dismiss();
                break;
        }
        if(mListener!=null){
            mListener.OnComandaAsyncFinish(datosRegresados,peticion);
        }else if(datosRegresados.getError()){
            Toast.makeText(context,context.getString(R.string.msj_error_limpiar_comandas)+" : "+Exception.class.cast(datosRegresados.getDatosRegresados()).getMessage().toString(),Toast.LENGTH_SHORT).show();
        }

    }


    private void obtenerComanda(){

        Type typeListaComanda = new TypeToken<List<Comanda>>(){}.getType();
        Type typeMesaSeleccionada = new TypeToken<Mesa>(){}.getType();
        Type typeCuentaSeleccionada = new TypeToken<Cuenta>(){}.getType();

        comanda = new Comanda();

        if(stringListaComandas!=""){
            lstComandas = new Gson().fromJson(stringListaComandas,typeListaComanda);
        }else{
            lstComandas = new ArrayList<>();
        }

//        // codigo temporal para limpiar la mesa
//        for (int i = lstComandas.size() - image_nav_header_default_comanda; i >= 0; i--) {
//            if(lstComandas.get(i).getCOD_MESA()==null){
//                lstComandas.remove(i);
//            }
//        }



        mesaSeleccionada = new Gson().fromJson(stringMesaSeleccionada,typeMesaSeleccionada);
        cuentaSeleccionada = new Gson().fromJson(stringCuentaSeleccionada,typeCuentaSeleccionada);

        if(mesaSeleccionada != null || stringMesaSeleccionada != "") {
            comanda.setCOD_MESA(mesaSeleccionada.getCodigo());
        }

        if(lstComandas.contains(comanda)){
            if(lstComandas.get(lstComandas.indexOf(comanda)).getListas()!=null){
                comanda = lstComandas.get(lstComandas.indexOf(comanda));
            }else{
                comanda.setListas( new ArrayList<ComandaArticulo>());
            }
        }else{
            comanda.setListas( new ArrayList<ComandaArticulo>());
        }

        stringListaComandas = "";
        stringMesaSeleccionada = "";
    }

    public void addToCart(ComandaArticulo comandaArticuloSeleccionado) throws Exception{

//        if(lstComandas.contains(comanda)){
//
//            if(lstComandas.get(lstComandas.indexOf(comanda)).getListas().contains(comandaArticuloSeleccionado)){
//                int indexComanda = lstComandas.get(lstComandas.indexOf(comanda)).getListas().indexOf(comandaArticuloSeleccionado);
//
//                ComandaArticulo comandaArticuloExistente = lstComandas.get(lstComandas.indexOf(comanda)).getListas().get(indexComanda);
//                comandaArticuloSeleccionado.setPRE_ART(comandaArticuloExistente.getPRE_ART());
//
//            }else{
//                comandaArticuloSeleccionado = obtenerPrecio(comandaArticuloSeleccionado);
//            }
//
//            lstComandas.get(lstComandas.indexOf(comanda)).getListas().add(comandaArticuloSeleccionado);
//        }else{
//            comandaArticuloSeleccionado = obtenerPrecio(comandaArticuloSeleccionado);


        if(comanda.getListas()!=null){
            comandaArticuloSeleccionado.setNUM_CTA(Integer.valueOf(cuentaSeleccionada.getCuenta()));
            comanda.getListas().add(comandaArticuloSeleccionado);
        }else{
            List<ComandaArticulo> lstcomandaArticulos = new ArrayList<>();
            comandaArticuloSeleccionado.setNUM_CTA(Integer.valueOf(cuentaSeleccionada.getCuenta()));
            lstcomandaArticulos.add(comandaArticuloSeleccionado);
            comanda.setListas(lstcomandaArticulos);
        }


        if(comanda.getCOD_MESA()!=null){


            if(lstComandas.contains(comanda)){
                int indexComanda = lstComandas.indexOf(comanda);
                lstComandas.set(indexComanda,comanda);
            }else{
                lstComandas.add(comanda);
            }

        }else{
            throw new Exception(context.getString(R.string.msj_no_cargo_mesa_comanda));
        }

//        }

        stringListaComandas = new Gson().toJson(lstComandas);

        editor.putString(context.getString(R.string.SHPTAGComandasPendientes),stringListaComandas);
        editor.commit();

    }

    public DatosRegresados obtenerPrecio(ComandaArticulo comandaArticuloSeleccionado){
        DatosRegresados datosRegresados = new DatosRegresados();
        try {
            if (configuracion != null) {

                WebService webService = new WebService(configuracion.getEndPointServicioPrincipal());

                String res = webService.PrecioArt(comandaArticuloSeleccionado.getCOD_ART());

                if (res != "") {
                    comandaArticuloSeleccionado.setPRE_ART(Double.valueOf(res));
                    datosRegresados.setDatosRegresados(comandaArticuloSeleccionado);
                    datosRegresados.setError(false);
                } else {
                    throw new Exception(context.getString(R.string.msj_error_no_hay_resultado));
                }


            } else {
                throw new Exception( context.getString(R.string.msj_no_cargo_configuracion));
            }
        }
        catch (Exception ex){

            datosRegresados.setError(true);
            datosRegresados.setDatosRegresados(ex);
//                Toast.makeText(getContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return datosRegresados;
    }

    public void editarComandaArticulo(ComandaArticulo articulo,int posicion){
        articulo.setPosition(-1);

        lstComandas.get(lstComandas.indexOf(comanda)).getListas().set(posicion,articulo);

        stringListaComandas = new Gson().toJson(lstComandas);

        editor.putString(context.getString(R.string.SHPTAGComandasPendientes),stringListaComandas);
        editor.commit();
    }


    private void deleteArticulos(List<Integer> selectedItemPositions) {

        List<ComandaArticulo> lstArticulo = new ArrayList<>(comanda.getListas());

        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {

            int position = selectedItemPositions.get(i);

            lstArticulo.remove(position);
        }

        comanda.setListas(lstArticulo);

        updateComanda(comanda);

    }


    private void updateComanda(Comanda comanda) {


        lstComandas.set(lstComandas.indexOf(comanda),comanda);

        editor.putString(context.getString(R.string.SHPTAGComandasPendientes),new Gson().toJson(lstComandas));
        editor.commit();
    }

    private void deleteComanda(Comanda comanda){

        int index = lstComandas.indexOf(comanda);

        lstComandas.remove(index);
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.SHPName), MODE_PRIVATE).edit();
        editor.putString(context.getString(R.string.SHPTAGComandasPendientes),new Gson().toJson(lstComandas));
        editor.commit();
    }

    private void limpiarComandas(){
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.SHPName), MODE_PRIVATE).edit();
        editor.putString(context.getString(R.string.SHPTAGComandasPendientes),"");
        editor.commit();
    }

}
