package pacificsoft.pscomandera;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Adapter.AdapterModificadorDetalle;
import pacificsoft.pscomandera.Adapter.AdapterNota;
import pacificsoft.pscomandera.Adapter.AdapterNotasRecyclerView;
import pacificsoft.pscomandera.Enum.EnumTipoPeticionAsyncTask;
import pacificsoft.pscomandera.Enum.StaticVariables;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.Loading.LoadingTask;
import pacificsoft.pscomandera.Modelo.ComandaArticulo;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.GenericSendData;
import pacificsoft.pscomandera.Modelo.Modificador;
import pacificsoft.pscomandera.Modelo.ModificadorSeleccionado;
import pacificsoft.pscomandera.Modelo.Nota;
import pacificsoft.pscomandera.Util.CargarConfiguracion;
import pacificsoft.pscomandera.Util.ComandaAsync;

public class DetalleArticuloComandaActivity extends AppCompatActivity implements CargarConfiguracion.CargaConfiguracionListener,ComandaAsync.ComandaAsyncTaskListener , LoadingTask.LoadingTaskListener {

    private ImageView imagenPlatillo;
    private MaterialRippleLayout rippleButtonGuardarNota, rippleImageViewAtras, rippleImageButtonAgregarNota,rippleMasCantidad,rippleMenosCantidad;
    private Button guardarCambios;
    private TextView textViewDescripcionArticulo, textViewPrecio;
    private EditText editTextNotas,editTextCantidad;
    private Spinner spinnerNotas;
    private RecyclerView recyclerViewNotas,recyclerViewModificadorDetalles;
    private CheckBox checkBoxSwitchTexto;

    private Configuracion configuracion;
    private int actividadPadre;
    private double total = 0.0;
    private ComandaArticulo articuloSeleccionado = null;
    AdapterNotasRecyclerView adapterNotaRecyclerView;

    private ActionMode actionMode;
    private ActionModeCallback actionModeCallback;

    private DecimalFormat decimalFormat;


//    Usuario usuario = new Usuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_articulo_comanda);
//        toolbar = findViewById(R.id.toolbar);

        imagenPlatillo = findViewById(R.id.imagenPlatillo);
        textViewDescripcionArticulo = findViewById(R.id.textViewDescripcionArticulo);
        textViewPrecio = findViewById(R.id.textViewPrecio);
        spinnerNotas = findViewById(R.id.spinnerNotas);
        editTextNotas = findViewById(R.id.editTextNotas);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        recyclerViewNotas = findViewById(R.id.recyclerViewNotas);
        recyclerViewModificadorDetalles = findViewById(R.id.recyclerViewModificadorDetalles);
        checkBoxSwitchTexto = findViewById(R.id.checkBoxSwitchTexto);

        rippleButtonGuardarNota = findViewById(R.id.rippleButtonGuardarNota);
        rippleImageButtonAgregarNota = findViewById(R.id.rippleImageButtonAgregarNota);
        rippleImageViewAtras = findViewById(R.id.rippleImageViewAtras);
        rippleMenosCantidad = findViewById(R.id.rippleMenosCantidad);
        rippleMasCantidad = findViewById(R.id.rippleMasCantidad);

        guardarCambios = findViewById(R.id.guardarCambios);

//        configureToolbar();

        actividadPadre = getIntent().getIntExtra(getString(R.string.dato_extra_intent_request), -1);

        articuloSeleccionado = ComandaArticulo.class.cast(getIntent().getSerializableExtra(getString(R.string.dato_extra_articulo_comanda)));

        if (articuloSeleccionado.getNotasList() == null || articuloSeleccionado.getNotasList().size() <= 0) {
            articuloSeleccionado.setNotasList(new ArrayList<String>());
        }

        editTextCantidad.setText(String.valueOf(articuloSeleccionado.getCAN_PRO()));

        decimalFormat = new DecimalFormat("#0.00");


        /* CUANDO ESTE LOS DISEÑOS COMPLETOS SE ELIGIRA SI SE CAMBIA SI EL LLAMADO VIENE DESDE COMADNAACTIVITY O SI VIENE DESDE GRUPOACTIVITY*/
//        switch (actividadPadre){
//            case StaticVariables.REQUEST.COMANDA_ACTIVITY:
//                break;
//        }


        if (actividadPadre == StaticVariables.REQUEST.GRUPO_ACTIVITY || actividadPadre == StaticVariables.REQUEST.MODIFICADOR_ACTIVITY) {
            guardarCambios.setText(getString(R.string.msj_comanda_button_agregar_articulo));
            articuloSeleccionado.setCAN_PRO(1);
        }

        editTextCantidad.setText(String.valueOf(articuloSeleccionado.getCAN_PRO()));

        rippleImageButtonAgregarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String notas = "";

                if (checkBoxSwitchTexto.isChecked()) {
                    notas = editTextNotas.getText().toString();
                    editTextNotas.setText("");
                    editTextNotas.setSelection(0);
                } else {
                    Nota notaSeleccionada = Nota.class.cast(spinnerNotas.getSelectedItem());
                    notas = notaSeleccionada.getDescripcion();
                    spinnerNotas.setSelection(0);
                }

                if (!notas.equals("")) {
                    articuloSeleccionado.getNotasList().add(notas);
                    adapterNotaRecyclerView.notifyDataSetChanged();
                }
//                notas+=notaSeleccionada.getDescripcion()+"\n";
//
//                editTextNotas.setText(notas);
//                int pos = editTextNotas.getText().length();
//                editTextNotas.setSelection(pos);
            }
        });

        rippleMenosCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restarCantidad();
            }
        });

        rippleMasCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumarCantidad();
            }
        });

        checkBoxSwitchTexto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextNotas.setVisibility(View.VISIBLE);
                    spinnerNotas.setVisibility(View.GONE);
                } else {
                    editTextNotas.setVisibility(View.GONE);
                    spinnerNotas.setVisibility(View.VISIBLE);
                }
            }
        });

        editTextCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    double cantidadProducto  = Double.valueOf(String.valueOf(s));

                    articuloSeleccionado.setCAN_PRO(cantidadProducto);

                    obtenerTotalArticulo();
                }catch (Exception ex){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        rippleButtonGuardarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rippleButtonGuardarNota.setEnabled(false);
                hideKeyboard(DetalleArticuloComandaActivity.this);
                llamarComandaAsync();
//                enviarComanda();
            }
        });

        rippleImageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        actionModeCallback = new ActionModeCallback();


//        actionModeCallback = new ComandaActivity.ActionModeCallback();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        CargarConfiguracion cargarConfiguracion = new CargarConfiguracion(this, DetalleArticuloComandaActivity.this);
        cargarConfiguracion.execute();
    }

    @Override
    public void cargaConfiguracionCompleta(DatosRegresados datosregresados) {
        if (!datosregresados.getError()) {
            configuracion = Configuracion.class.cast(datosregresados.getDatosRegresados());

            if (actividadPadre == StaticVariables.REQUEST.GRUPO_ACTIVITY) {
                GenericSendData genericSendData = new GenericSendData();
                genericSendData.setObjectSendData(articuloSeleccionado);

                ComandaAsync comandaAsync = new ComandaAsync(DetalleArticuloComandaActivity.this, StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_PRECIO_ARTICULO, configuracion, genericSendData, this);
                comandaAsync.execute();
            } else {
                cargarDatosArticulo();
            }

            if(articuloSeleccionado.getModificable()==1){
                AdapterModificadorDetalle adapterModificadorDetalle = new AdapterModificadorDetalle(DetalleArticuloComandaActivity.this,getModificadores());

                StaggeredGridLayoutManager GLayoutManager = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.columnsGridViewModificadorDetalle), StaggeredGridLayoutManager.VERTICAL);

                recyclerViewModificadorDetalles.setLayoutManager(GLayoutManager);
                recyclerViewModificadorDetalles.setHasFixedSize(true);
                recyclerViewModificadorDetalles.setAdapter(adapterModificadorDetalle);
            }

//            cargarDatosArticulo();
        } else {
            configuracion = null;
        }
    }

    @Override
    public void OnComandaAsyncFinish(DatosRegresados datosRegresados, int peticion) {

        int result = -1;

        if (!datosRegresados.getError()) {
            switch (peticion) {
                case StaticVariables.PETICION_COMANDA_ASYNC.AGREGAR_ARTICULO:
                    result = StaticVariables.RESULT_ACTIVITY.SE_AGREGO_CORRECTAMENTE;

                    setResult(result);

                    finish();
                    break;

                case StaticVariables.PETICION_COMANDA_ASYNC.EDITAR_COMANDA:
                    result = StaticVariables.RESULT_ACTIVITY.SE_EDITO_CORRECTAMENTE;

                    setResult(result);

                    finish();
                    break;
                case StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_PRECIO_ARTICULO:

                    ComandaArticulo articulo = ComandaArticulo.class.cast(datosRegresados.getDatosRegresados());

                    articuloSeleccionado.setPRE_ART(articulo.getPRE_ART());

                    cargarDatosArticulo();


                    break;
            }
        } else if (peticion != StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_PRECIO_ARTICULO) {

            result = StaticVariables.RESULT_ACTIVITY.ERROR;

            setResult(result);

            finish();

        }

//        if(!datosRegresados.getError()){
//            result = StaticVariables.RESULT_ACTIVITY.SE_EDITO_CORRECTAMENTE;
//        }else{
//            result = StaticVariables.RESULT_ACTIVITY.ERROR;
//        }


    }

    @Override
    public void onLoadedDataLoadingTask(EnumTipoPeticionAsyncTask peticion, DatosRegresados datosregresados) {
        if (!datosregresados.getError()) {

            switch (peticion) {
                case OBTENER_NOTAS:
                    List<Nota> lstdatos = (List<Nota>) datosregresados.getLstDatosRegresados();

                    AdapterNota adapterNota = new AdapterNota(lstdatos, DetalleArticuloComandaActivity.this);
                    spinnerNotas.setAdapter(adapterNota);
                    break;
            }

        } else {
            Toast.makeText(DetalleArticuloComandaActivity.this, Exception.class.cast(datosregresados.getDatosRegresados()).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


//    private void configureToolbar() {
//
//        setSupportActionBar(toolbar);
//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
//        actionbar.setDisplayHomeAsUpEnabled(true);
//
//    }

    private void llamarComandaAsync() {

        GenericSendData genericSendData = new GenericSendData();
        genericSendData.setObjectSendData(articuloSeleccionado);

        ComandaAsync comandaAsync = new ComandaAsync(DetalleArticuloComandaActivity.this, actividadPadre == StaticVariables.REQUEST.GRUPO_ACTIVITY || actividadPadre == StaticVariables.REQUEST.MODIFICADOR_ACTIVITY ? StaticVariables.PETICION_COMANDA_ASYNC.AGREGAR_ARTICULO : StaticVariables.PETICION_COMANDA_ASYNC.EDITAR_COMANDA, configuracion, genericSendData, this);
        comandaAsync.execute();
    }

    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void cargarDatosArticulo() {

        if (articuloSeleccionado.getImagen() != null && articuloSeleccionado.getImagen() != "") {
            byte[] decodedString = Base64.decode(articuloSeleccionado.getImagen(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imagenPlatillo.setImageBitmap(decodedByte);
        }

//        textViewPrecio.setText(decimalFormat.format(articuloSeleccionado.getPRE_ART()));

        obtenerTotalArticulo();

//        editTextNotas.setText(articuloSeleccionado.getNOTA());
        cargarNotasArticulo();
        textViewDescripcionArticulo.setText(articuloSeleccionado.getDES_PRO());

        LoadingTask loadingTask = new LoadingTask(new WebService(configuracion.getEndPointServicioPrincipal()), DetalleArticuloComandaActivity.this, EnumTipoPeticionAsyncTask.OBTENER_NOTAS, getString(R.string.msj_obteniendo_notas), articuloSeleccionado, this, true);
        loadingTask.execute();
    }

    private void cargarNotasArticulo() {
        adapterNotaRecyclerView = new AdapterNotasRecyclerView(DetalleArticuloComandaActivity.this, articuloSeleccionado.getNotasList());
        adapterNotaRecyclerView.setListenerNotas(new AdapterNotasRecyclerView.AdapterNotaListener() {
            @Override
            public void onItemLongClick(View view, String obj, int pos) {
                enableActionMode(pos);
            }

            @Override
            public void onItemClick(View view, String obj, int pos) {
                if (adapterNotaRecyclerView.getSelectedItemCount() > 0) {
                    enableActionMode(pos);
                }
            }
        });

        StaggeredGridLayoutManager GLayoutManager = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.columnsGridViewNotas), StaggeredGridLayoutManager.VERTICAL);

        recyclerViewNotas.setLayoutManager(GLayoutManager);
        recyclerViewNotas.setHasFixedSize(true);
        recyclerViewNotas.setAdapter(adapterNotaRecyclerView);

        if (actionMode != null) {
            toggleSelection(-1);
        }

    }

    private void enableActionMode(int position) {

        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        if (position >= 0) {
            adapterNotaRecyclerView.toggleSelection(position);
        }

        int count = adapterNotaRecyclerView.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_eliminar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_eliminar) {
                deleteNota();
                mode.finish();

                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapterNotaRecyclerView.clearSelections();
            actionMode = null;

//            mode.setSystemBarColor(MultiSelect.this, R.color.colorPrimary);
        }
    }

    private void deleteNota() {
        List<Integer> selectedItemPositions = adapterNotaRecyclerView.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            adapterNotaRecyclerView.removeData(selectedItemPositions.get(i));
//            deleteComandaArticulo(selectedItemPositions.get(i));
        }
    }

    private void sumarCantidad(){
        double cantidad = articuloSeleccionado.getCAN_PRO() +1;
        articuloSeleccionado.setCAN_PRO(cantidad>0?cantidad:articuloSeleccionado.getCAN_PRO());
        editTextCantidad.setText(String.valueOf(articuloSeleccionado.getCAN_PRO()));

        obtenerTotalArticulo();
    }

    private void restarCantidad(){
        double cantidad = articuloSeleccionado.getCAN_PRO() -1;
        articuloSeleccionado.setCAN_PRO(cantidad>0?cantidad:articuloSeleccionado.getCAN_PRO());
        editTextCantidad.setText(String.valueOf(articuloSeleccionado.getCAN_PRO()));

        obtenerTotalArticulo();
    }

    private void obtenerTotalArticulo(){
        textViewPrecio.setText(String.valueOf(articuloSeleccionado.getCAN_PRO() * articuloSeleccionado.getPRE_ART()));

//        textViewTotal.setText(String.valueOf(articuloSeleccionado.getCAN_PRO() * articuloSeleccionado.getPRE_ART()));
    }

    private List<Modificador> getModificadores(){
        List<Modificador> lstModificadores = new ArrayList<>();
        for(ModificadorSeleccionado modificadorSeleccionado : articuloSeleccionado.getModificadorSeleccionados()){

            for(Modificador modificador: modificadorSeleccionado.getModificadoresSeleccionados()){
                lstModificadores.add(modificador);
            }
        }
        return lstModificadores;
    }

}
