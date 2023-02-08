package pacificsoft.pscomandera;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.tooltip.Tooltip;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pacificsoft.pscomandera.Adapter.ViewPagerAdapterModificadores;
import pacificsoft.pscomandera.Enum.EnumTipoPeticionAsyncTask;
import pacificsoft.pscomandera.Enum.StaticVariables;
import pacificsoft.pscomandera.Fragment.FragmentModificador;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.Loading.LoadingTask;
import pacificsoft.pscomandera.Modelo.ComandaArticulo;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.GenericSendData;
import pacificsoft.pscomandera.Modelo.Modificador;
import pacificsoft.pscomandera.Modelo.ModificadorSeleccionado;
import pacificsoft.pscomandera.Modelo.Receta;
import pacificsoft.pscomandera.Util.CargarConfiguracion;
import pacificsoft.pscomandera.Util.ComandaAsync;
import pacificsoft.pscomandera.Util.CustomViewPager;

public class ModificadoresActivity extends AppCompatActivity implements FragmentModificador.ModificadorFragmentListener, CargarConfiguracion.CargaConfiguracionListener,LoadingTask.LoadingTaskListener, ComandaAsync.ComandaAsyncTaskListener {

    private Receta receta;
    private ComandaArticulo articuloSeleccionado = null;
    private int actividadPadre, indexCurrentFragment = -1;
    private double precioTotalItemsSeleccionadosCurrentFragment = 0.0;
    private DecimalFormat decimalFormat ;
    private ViewPagerAdapterModificadores viewPagerAdapterModificadores;

    private CustomViewPager viewPagerModificadores;
    private MaterialRippleLayout rippleCancelar,rippleButtonSiguiente;
    private TextView textViewTitulo,textViewSubtitulo,textViewCantidadMaximo,textViewTotalPrecio,textViewDescripcionMaximo;
    Modificador modificadorfragmentActivo = null;
    private boolean saveLastFragmentData = false;
    private Configuracion configuracion;
    private WebService webService;

    private double totalPrecioArticuloTemp = 0.0;
    private boolean habilitarBotonSiguiente = true;
    private boolean savedInstance = false,isLocked = false;
    private int cantidadTotalCurrentFragment = 0;

    private SharedPreferences.Editor editor;
    private SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificadores);

        viewPagerModificadores = findViewById(R.id.viewPagerModificadores);
        rippleCancelar = findViewById(R.id.rippleCancelar);
        rippleButtonSiguiente = findViewById(R.id.rippleButtonSiguiente);
        textViewTitulo = findViewById(R.id.textViewTitulo);
        textViewSubtitulo = findViewById(R.id.textViewSubTitulo);
        textViewDescripcionMaximo = findViewById(R.id.textViewDescripcionMaximo);
        textViewCantidadMaximo = findViewById(R.id.textViewCantidadMaximo);
        textViewTotalPrecio = findViewById(R.id.textViewTotalPrecio);

        decimalFormat = new DecimalFormat("#0.00");


        rippleButtonSiguiente.setEnabled(habilitarBotonSiguiente);

        mPreferences = ModificadoresActivity.this.getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE);


        rippleCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("currentFragmentIndex");
                editor.commit();
                setResult(StaticVariables.RESULT_ACTIVITY.SIN_ACCION);
                finish();
            }
        });

        editor = getSharedPreferences(getString(R.string.SHPName),MODE_PRIVATE).edit();

        rippleButtonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(habilitarBotonSiguiente){
                    FragmentModificador frag1 = (FragmentModificador)viewPagerModificadores
                            .getAdapter()
                            .instantiateItem(viewPagerModificadores, viewPagerModificadores.getCurrentItem());
                    frag1.onClickButtonSiguiente();
                }

            }
        });

        textViewTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tooltip.Builder builder = new Tooltip.Builder(v, R.style.TooltipModificadoresTopHeader)
                        .setCancelable(true)
                        .setDismissOnClick(false)
                        .setText(articuloSeleccionado.getDES_PRO());
                builder.show();

            }
        });

        textViewSubtitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tooltip.Builder builder = new Tooltip.Builder(v, R.style.TooltipModificadoresTopHeader)
                        .setCancelable(true)
                        .setDismissOnClick(false)
                        .setText(modificadorfragmentActivo.getNombre());
                builder.show();
            }
        });

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        savedInstance = savedInstanceState.getBoolean("isSavedInstance");
        receta = (Receta) savedInstanceState.getSerializable("receta");
        configuracion = (Configuracion) savedInstanceState.getSerializable("configuracion");
        articuloSeleccionado = (ComandaArticulo) savedInstanceState.getSerializable("articuloSeleccionado");
        indexCurrentFragment = savedInstanceState.getInt("indexCurrentFragment");
        cantidadTotalCurrentFragment = savedInstanceState.getInt("totalItemsSeleccionadosCurrentFragment");
        precioTotalItemsSeleccionadosCurrentFragment = savedInstanceState.getDouble("precioTotalItemsSeleccionadosCurrentFragment");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean("isSavedInstance", true);
        savedInstanceState.putSerializable("receta",receta);
        savedInstanceState.putSerializable("configuracion",configuracion);
        savedInstanceState.putSerializable("articuloSeleccionado",articuloSeleccionado);
        savedInstanceState.putInt("indexCurrentFragment", indexCurrentFragment);
        savedInstanceState.putInt("totalItemsSeleccionadosCurrentFragment", cantidadTotalCurrentFragment);
        savedInstanceState.putDouble("precioTotalItemsSeleccionadosCurrentFragment",precioTotalItemsSeleccionadosCurrentFragment);



    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreferences = ModificadoresActivity.this.getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE);
        isLocked = mPreferences.getBoolean("isLocked",false);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {

        PowerManager powerManager = (PowerManager)ModificadoresActivity.this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenAwake = (Build.VERSION.SDK_INT < 20? powerManager.isScreenOn():powerManager.isInteractive());
        if( isScreenAwake) {
            editor.putBoolean("isLocked", false);
        } else {
            editor.putBoolean("isLocked", true);
        }


        editor.commit();

        super.onPause();
    }

    @Override
    protected void onPostResume() {

        actividadPadre = getIntent().getIntExtra(getString(R.string.dato_extra_intent_request),-1);

        if(!isLocked){
            if(!savedInstance){
                articuloSeleccionado = ComandaArticulo.class.cast(getIntent().getSerializableExtra(getString(R.string.dato_extra_articulo_comanda)));
                if(articuloSeleccionado!=null && articuloSeleccionado.getModificadorSeleccionados()==null){
                    articuloSeleccionado.setModificadorSeleccionados(new ArrayList<ModificadorSeleccionado>());
                }


                CargarConfiguracion cargarConfiguracion = new CargarConfiguracion(this,ModificadoresActivity.this);
                cargarConfiguracion.execute();
            }else{
                inicializarDatosModificadores();
            }
        }


        super.onPostResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case StaticVariables.REQUEST.DETALLE_COMANDA_AGREGAR_ACTIVITY:
                setResult(resultCode);
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        editor.remove("isLocked");
        editor.commit();
        super.finish();
    }

    @Override
    public void cargaConfiguracionCompleta(DatosRegresados datosregresados) {
        if(!datosregresados.getError()){
            configuracion = Configuracion.class.cast(datosregresados.getDatosRegresados());
            webService = new WebService(configuracion.getEndPointServicioPrincipal());

            GenericSendData genericSendData = new GenericSendData();
            genericSendData.setObjectSendData(articuloSeleccionado);

            ComandaAsync comandaAsync = new ComandaAsync(ModificadoresActivity.this, StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_PRECIO_ARTICULO,configuracion,genericSendData,this);
            comandaAsync.execute();
        }else{
            Toast.makeText(ModificadoresActivity.this,Exception.class.cast(datosregresados.getDatosRegresados()).getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadedDataLoadingTask(EnumTipoPeticionAsyncTask peticion, DatosRegresados datosregresados) {
        if(!datosregresados.getError()){
            switch (peticion){
                case OBTENER_MODIFICADORES:
                    receta = Receta.class.cast(datosregresados.getDatosRegresados());

                    inicializarDatosModificadores();

                    break;
            }
        }else{
            Toast.makeText(ModificadoresActivity.this,Exception.class.cast(datosregresados.getDatosRegresados()).getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializarDatosModificadores(){

        textViewTitulo.setText(articuloSeleccionado.getDES_PRO());


        if(receta.getListas().size()>0){

            if(!savedInstance){
                indexCurrentFragment = viewPagerModificadores.getCurrentItem();
                editor.putInt("currentFragmentIndex",indexCurrentFragment);
                editor.commit();
            }
        }

        viewPagerAdapterModificadores = new ViewPagerAdapterModificadores(getSupportFragmentManager(),receta.getListas().size(),receta.getListas(),ModificadoresActivity.this,indexCurrentFragment);
        viewPagerModificadores.setAdapter(viewPagerAdapterModificadores);

        totalPrecioArticuloTemp =  getTotalPrecioArticulo(precioTotalItemsSeleccionadosCurrentFragment);

        textViewTotalPrecio.setText(decimalFormat.format(totalPrecioArticuloTemp));

        mostrarOcultarElementos();

    }

    @Override
    public void OnComandaAsyncFinish(DatosRegresados datosRegresados, int peticion) {
        int result = -1;

        if(!datosRegresados.getError()) {
            switch (peticion) {
                case StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_PRECIO_ARTICULO:

                    ComandaArticulo articulo = ComandaArticulo.class.cast(datosRegresados.getDatosRegresados());

                    totalPrecioArticuloTemp = articulo.getPRE_ART();

                    articuloSeleccionado.setPRE_ART(articulo.getPRE_ART());

                    textViewTotalPrecio.setText(decimalFormat.format(articuloSeleccionado.getPRE_ART()));

                    cargarModificadores();

                    break;
            }
        }
    }

    private void cargarModificadores(){
        if(configuracion!=null && webService!=null){

            String idArticulo =  articuloSeleccionado.getCOD_ART();

            LoadingTask loadingTask = new LoadingTask(webService,ModificadoresActivity.this, EnumTipoPeticionAsyncTask.OBTENER_MODIFICADORES,getString(R.string.msj_obteniendo_modificadores),idArticulo,this,true);
            loadingTask.execute();
        }
    }

    private void mostrarOcultarElementos() {
        modificadorfragmentActivo = receta.getListas().get(indexCurrentFragment);

        ConstraintLayout contentSecondHeader = findViewById(R.id.contentSecondHeader);

        if (modificadorfragmentActivo != null) {
            if (modificadorfragmentActivo.getTipo().equals("3")) {

                if(contentSecondHeader!=null){
                    contentSecondHeader.setVisibility(View.VISIBLE);
                }else{
                    textViewSubtitulo.setVisibility(View.VISIBLE);
                    textViewDescripcionMaximo.setVisibility(View.VISIBLE);
                    textViewCantidadMaximo.setVisibility(View.VISIBLE);
                }

                textViewSubtitulo.setText(modificadorfragmentActivo.getNombre());
                textViewCantidadMaximo.setText(modificadorfragmentActivo.getMaximo() + "-0");

                validarBotonSiguiente();

            } else {

                if(contentSecondHeader!=null){
                    contentSecondHeader.setVisibility(View.GONE);
                }else{
                    textViewSubtitulo.setVisibility(View.GONE);
                    textViewDescripcionMaximo.setVisibility(View.GONE);
                    textViewCantidadMaximo.setVisibility(View.GONE);

                    textViewSubtitulo.setText("");
                    textViewCantidadMaximo.setText("0-0");
                }


            }
        }
    }

    @Override
    public void onClickItemModificador(double total,int totalCantidad) {
        precioTotalItemsSeleccionadosCurrentFragment = total;
        cantidadTotalCurrentFragment = totalCantidad;
        validarBotonSiguiente();
    }

    @Override
    public void onClickButtonSiguienteResponse(ModificadorSeleccionado modificadorSeleccionado) {

        //Aqui es la operacion para guardar los cambios

        if(habilitarBotonSiguiente){
            if(!saveLastFragmentData){
                articuloSeleccionado.getModificadorSeleccionados().add(modificadorSeleccionado);
            }

            articuloSeleccionado.setPRE_ART(totalPrecioArticuloTemp);

            if(viewPagerModificadores.getCurrentItem()>=(viewPagerAdapterModificadores.getCount()-1)){
                saveLastFragmentData = true;
                habilitarBotonSiguiente = false;

                Intent i = new Intent(ModificadoresActivity.this,DetalleArticuloComandaActivity.class);
                i.putExtra(getString(R.string.dato_extra_articulo_comanda),articuloSeleccionado);

                editor.remove("currentFragmentIndex");
                editor.commit();

                llamarActividad(StaticVariables.REQUEST.DETALLE_COMANDA_AGREGAR_ACTIVITY,i);

            }else{
                indexCurrentFragment = viewPagerModificadores.getCurrentItem()+1;
                viewPagerModificadores.setCurrentItem(indexCurrentFragment,false);
                precioTotalItemsSeleccionadosCurrentFragment = 0.0;
                cantidadTotalCurrentFragment = 0;


                editor.putInt("currentFragmentIndex",indexCurrentFragment);
                editor.commit();

                mostrarOcultarElementos();
            }
        }
    }


    private double getTotalPrecioArticulo(double totalArticuloSeleccionado){
        double total = 0;
        total = articuloSeleccionado.getPRE_ART() + totalArticuloSeleccionado;
        return total;
    }

    private void llamarActividad(int requestActivity, Intent i){
        int Parent = StaticVariables.REQUEST.MODIFICADOR_ACTIVITY;

        switch (requestActivity){
            case StaticVariables.REQUEST.DETALLE_COMANDA_AGREGAR_ACTIVITY:
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                startActivityForResult(i,requestActivity);
                break;
        }
    }

    private void actualizarestatusBotonSiguiente(){
        rippleButtonSiguiente.setEnabled(habilitarBotonSiguiente);
    }

    private void validarBotonSiguiente(){
        //Aqui se obtiene el obtiene el total del fragment cada vez que se selecciona o cambia de ingrediente

        if(modificadorfragmentActivo.getTipo().equals("3")){
            textViewCantidadMaximo.setText(modificadorfragmentActivo.getMaximo()+"-"+cantidadTotalCurrentFragment);

            if(cantidadTotalCurrentFragment > 0){
                habilitarBotonSiguiente = true;
            }else{
                habilitarBotonSiguiente = false;
                Toast.makeText(ModificadoresActivity.this,getString(R.string.msj_debe_seleccionar_articulo),Toast.LENGTH_SHORT).show();
            }

            actualizarestatusBotonSiguiente();
        }else{
            habilitarBotonSiguiente = true;
            actualizarestatusBotonSiguiente();
        }

        getTotalPrecioArticulo(precioTotalItemsSeleccionadosCurrentFragment);

        totalPrecioArticuloTemp =  getTotalPrecioArticulo(precioTotalItemsSeleccionadosCurrentFragment);

        textViewTotalPrecio.setText(decimalFormat.format(totalPrecioArticuloTemp));
    }
}
