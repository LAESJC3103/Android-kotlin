package pacificsoft.pscomandera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Adapter.AdapterComanda;
import pacificsoft.pscomandera.Enum.EnumTipoPeticionAsyncTask;
import pacificsoft.pscomandera.Enum.StaticVariables;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.Loading.LoadingTask;
import pacificsoft.pscomandera.Modelo.Comanda;
import pacificsoft.pscomandera.Modelo.ComandaArticulo;
import pacificsoft.pscomandera.Modelo.ComandaArticuloRequest;
import pacificsoft.pscomandera.Modelo.ComandaModificador;
import pacificsoft.pscomandera.Modelo.ComandaRequest;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.Cuenta;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.GenericSendData;
import pacificsoft.pscomandera.Modelo.Modificador;
import pacificsoft.pscomandera.Modelo.ModificadorSeleccionado;
import pacificsoft.pscomandera.Modelo.Usuario;
import pacificsoft.pscomandera.Util.CargarConfiguracion;
import pacificsoft.pscomandera.Util.ComandaAsync;
import pacificsoft.pscomandera.Util.CustomSnackbar;

public class ComandaActivity extends AppCompatActivity implements LoadingTask.LoadingTaskListener, CargarConfiguracion.CargaConfiguracionListener,ComandaAsync.ComandaAsyncTaskListener{

  Configuracion configuracion;

  TextView textViewTotal,textViewCuenta;
  MaterialRippleLayout buttonEnviarComanda,rippleImageViewAtras;
  RecyclerView reciclerViewComanda;

  SharedPreferences.Editor editor;
  SharedPreferences mPreferences;

  Comanda comanda;
  AdapterComanda adapterComanda;

  Usuario usuario = new Usuario();

  DecimalFormat decimalFormat ;

  CoordinatorLayout coordinator;

  private ActionMode actionMode;
  private ActionModeCallback actionModeCallback;
  Cuenta cuentaSeleccionada;

  ComandaAsync comandaAsync;

  Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_comanda);
    toolbar = findViewById(R.id.toolbar);

    buttonEnviarComanda = findViewById(R.id.buttonCerrarPedido);
    rippleImageViewAtras = findViewById(R.id.rippleImageViewAtras);
    reciclerViewComanda = findViewById(R.id.reciclerViewComanda);
    textViewTotal = findViewById(R.id.textViewTotal);
    textViewCuenta = findViewById(R.id.textViewCuenta);
    coordinator = findViewById(R.id.coordinator);

    configureToolbar();

    mPreferences = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE);

    editor = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE).edit();
    ;

    String stringUsuario = mPreferences.getString(getString(R.string.SHPTAGUser),"");
    String jsonCuentaSeleccionada = mPreferences.getString(getString(R.string.SHPTAGCuentaSeleccionada),"");


    Type typeCuenta = new TypeToken<Cuenta>(){}.getType();
    cuentaSeleccionada = new Gson().fromJson(jsonCuentaSeleccionada,typeCuenta);

    textViewCuenta.setText(getString(R.string.nav_drawer_cuenta)+" "+cuentaSeleccionada.getCuenta());

    Type typeUsuario = new TypeToken<Usuario>(){}.getType();

    usuario = new Gson().fromJson(stringUsuario,typeUsuario);

    decimalFormat = new DecimalFormat("#0.00");

    buttonEnviarComanda.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        buttonEnviarComanda.setEnabled(false);
        enviarComanda();
      }
    });

    rippleImageViewAtras.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    actionModeCallback = new ActionModeCallback();
  }

  @Override
  protected void onPostResume() {

    CargarConfiguracion cargarConfiguracion = new CargarConfiguracion(this,ComandaActivity.this);
    cargarConfiguracion.execute();

    super.onPostResume();
  }

  @Override
  public void cargaConfiguracionCompleta(DatosRegresados datosregresados) {
    if(!datosregresados.getError()){
      configuracion = Configuracion.class.cast(datosregresados.getDatosRegresados());

      loadComanda();

//            AsyncCargarComanda asyncCargarComanda = new AsyncCargarComanda();
//            asyncCargarComanda.execute();
    }else{
      buttonEnviarComanda.setEnabled(false);

      Toast.makeText(ComandaActivity.this,getString(R.string.msj_error_cargar_configuracion),Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void OnComandaAsyncFinish(DatosRegresados datosRegresados, int peticion) {
    if(!datosRegresados.getError()){

      switch (peticion){
        case StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_COMANDA:
          comanda = Comanda.class.cast(datosRegresados.getDatosRegresados());
          cargarComanda();
          break;
        case StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_COMANDA:
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
          finish();
          break;
      }

    }else{
      Toast.makeText(ComandaActivity.this,Exception.class.cast(datosRegresados.getDatosRegresados()).getMessage(),Toast.LENGTH_SHORT).show();

      switch (peticion){
        case StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_ARTICULOS:{
          loadComanda();
          break;
        }
        case StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_COMANDA:
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
          break;
      }
    }
  }

  @Override
  public void onLoadedDataLoadingTask(EnumTipoPeticionAsyncTask peticion, DatosRegresados datosregresados) {
    switch (peticion){
      case ORDEN_COMANDA:
        if(!datosregresados.getError()){
          setResult(StaticVariables.RESULT_ACTIVITY.COMANDA_ENVIADA);

          comandaAsync = new ComandaAsync(ComandaActivity.this,StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_COMANDA,configuracion,null,this);
          comandaAsync.execute();

//                    deleteComanda();
          Toast.makeText(ComandaActivity.this,getString(R.string.msj_comanda_enviada_exitosamente),Toast.LENGTH_SHORT).show();

        }else{
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
          Toast.makeText(ComandaActivity.this,Exception.class.cast(datosregresados.getDatosRegresados()).getMessage(),Toast.LENGTH_SHORT).show();
        }
        buttonEnviarComanda.setEnabled(true);
        break;
    }
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
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==StaticVariables.REQUEST.DETALLE_COMANDA_EDITAR_ACTIVITY){
      if(resultCode == StaticVariables.RESULT_ACTIVITY.SE_EDITO_CORRECTAMENTE){

        CustomSnackbar customSnackbar = CustomSnackbar.make(coordinator, CustomSnackbar.LENGTH_SHORT);
        customSnackbar.setText(getString(R.string.msj_editado_correctamente));
        customSnackbar.setAction(getString(R.string.msj_boton_aceptar), new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            // TODO: handle click here
          }
        });
        customSnackbar.show();

        loadComanda();

//                String mensajeSNDACTIVIDAD = data.getStringExtra("Texto");
//                Toast.makeText(ComandaActivity.this,mensajeSNDACTIVIDAD,Toast.LENGTH_LONG).show();
      }
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    comandaAsync.cancel(true);
  }

  private void configureToolbar() {

    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    actionbar.setDisplayHomeAsUpEnabled(true);

  }

  private void loadComanda(){
    comandaAsync = new ComandaAsync(ComandaActivity.this,StaticVariables.PETICION_COMANDA_ASYNC.OBTENER_COMANDA,configuracion,null,this);

    comandaAsync.execute();
  }

  private void cargarComanda(){

    adapterComanda = new AdapterComanda(comanda.getListas(),this);

    adapterComanda.setOnClickListener(new AdapterComanda.AdapterComandaListener() {
      @Override
      public void onItemClick(View view, ComandaArticulo obj, int pos) {
        if (adapterComanda.getSelectedItemCount() > 0) {
          enableActionMode(pos);
        }
//                else {
//                    // read the inbox which removes bold from the row
//                    ComandaArticulo articulo = adapterComanda.getItem(pos);
//
//                    articulo.setPosition(pos);
//
//                    Intent intent = new Intent(ComandaActivity.this,DetalleArticuloComandaActivity.class);
//                    intent.putExtra(getString(R.string.dato_extra_articulo_comanda),articulo);
//
//                    llamarActividad(StaticVariables.REQUEST.DETALLE_COMANDA_EDITAR_ACTIVITY,intent);
//
////                    startActivityForResult(intent,StaticVariables.REQUEST.DETALLE_COMANDA_EDITAR_ACTIVITY);
////                    Toast.makeText(getApplicationContext(), "Producto: " + articulo.getDES_PRO(), Toast.LENGTH_SHORT).show();
//                }
      }

      @Override
      public void onItemLongClick(View view, ComandaArticulo obj, int pos) {
        enableActionMode(pos);
      }

      @Override
      public void onItemClickEditar(View view, ComandaArticulo obj, int pos) {
//                 read the inbox which removes bold from the row
        ComandaArticulo articulo = adapterComanda.getItem(pos);
        articulo.setExpanded(false);

        articulo.setPosition(pos);

        Intent intent = new Intent(ComandaActivity.this,DetalleArticuloComandaActivity.class);
        intent.putExtra(getString(R.string.dato_extra_articulo_comanda),articulo);

        llamarActividad(StaticVariables.REQUEST.DETALLE_COMANDA_EDITAR_ACTIVITY,intent);

//                    startActivityForResult(intent,StaticVariables.REQUEST.DETALLE_COMANDA_EDITAR_ACTIVITY);
//                    Toast.makeText(getApplicationContext(), "Producto: " + articulo.getDES_PRO(), Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onItemClickDesplegar(View view, ComandaArticulo obj, int pos) {

        RecyclerView.LayoutParams layoutParamRecycler = (RecyclerView.LayoutParams)reciclerViewComanda.getLayoutParams();

        reciclerViewComanda.setLayoutParams(layoutParamRecycler);
      }
    });



    StaggeredGridLayoutManager  GLayoutManager = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.columnsGridViewComanda),StaggeredGridLayoutManager.VERTICAL);

    reciclerViewComanda.setLayoutManager(GLayoutManager);
    reciclerViewComanda.setHasFixedSize(true);
    reciclerViewComanda.setAdapter(adapterComanda);

    if(actionMode!=null){
      toggleSelection(-1);
    }

    updateTotal();
  }

  private void llamarActividad(int requestActivity,Intent i){


    int Parent = StaticVariables.REQUEST.COMANDA_ACTIVITY;

    switch (requestActivity){
      case StaticVariables.REQUEST.DETALLE_COMANDA_EDITAR_ACTIVITY:
        i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
        startActivityForResult(i,requestActivity);
        break;
      case StaticVariables.REQUEST.COMANDA_ACTIVITY:
        i = new Intent(ComandaActivity.this,ComandaActivity.class);
        i.putExtra(getString(R.string.dato_extra_intent_request), Parent);

        startActivityForResult(i,requestActivity);
        break;
      case StaticVariables.REQUEST.ACERCA_DE_ACTIVITY:
        i = new Intent(ComandaActivity.this,AcercaDeActivity.class);
        i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
        startActivityForResult(i,requestActivity);
        break;
    }
  }



  private void updateTotal(){
    double total = getTotal();

    textViewTotal.setText(getString(R.string.lbl_total)+" "+getString(R.string.signo_moneda)+ decimalFormat.format(total));
  }

  private void enviarComanda(){
    if(comanda.getListas().size()>0){
      WebService webService = new WebService(configuracion.getEndPointServicioPrincipal());

      comanda.setALM_MP(configuracion.getAlmacenMateriaPrima());
      comanda.setCOD_ALM(configuracion.getAlmacenMercancia());
      comanda.setCOD_USU(usuario.getCodigo());

      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

      LoadingTask loadingTask = new LoadingTask(webService,ComandaActivity.this, EnumTipoPeticionAsyncTask.ORDEN_COMANDA,getString(R.string.msj_enviando_comanda),comanda,this,true);
      loadingTask.execute();
    }
    else{
      Toast.makeText(ComandaActivity.this,getString(R.string.msj_comanda_vacia),Toast.LENGTH_LONG).show();
    }

  }


  private double getTotal(){
    double total = 0.0;

    for (ComandaArticulo comandaArticulo: comanda.getListas()){
      total += (comandaArticulo.getPRE_ART()* comandaArticulo.getCAN_PRO());
    }


    return total;
  }

  private void enableActionMode(int position) {
    toolbar.animate().alpha(0).setDuration(500).start();
    toolbar.setVisibility(View.INVISIBLE);

    if (actionMode == null) {
      actionMode = startSupportActionMode(actionModeCallback);
    }
    toggleSelection(position);
  }

  private void toggleSelection(int position) {
    if(position>=0){
      adapterComanda.toggleSelection(position);
    }

    int count = adapterComanda.getSelectedItemCount();

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
        deleteArticulo();
        mode.finish();

        return true;
      }
      return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
      adapterComanda.clearSelections();
      actionMode = null;
      toolbar.animate().alpha(1).setDuration(500).start();

      toolbar.setVisibility(View.VISIBLE);

//            mode.setSystemBarColor(MultiSelect.this, R.color.colorPrimary);
    }
  }


  private void deleteArticulo() {


    List<Integer> selectedItemPositions = adapterComanda.getSelectedItems();
    for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
      adapterComanda.removeData(selectedItemPositions.get(i));
//            deleteComandaArticulo(selectedItemPositions.get(i));
    }

    updateTotal();

    adapterComanda.notifyDataSetChanged();

    GenericSendData genericSendData = new GenericSendData();
    genericSendData.setListSendData(selectedItemPositions);

    comandaAsync = new ComandaAsync(ComandaActivity.this,StaticVariables.PETICION_COMANDA_ASYNC.ELIMINAR_ARTICULOS,configuracion,genericSendData,this);
    comandaAsync.execute();
  }





}
