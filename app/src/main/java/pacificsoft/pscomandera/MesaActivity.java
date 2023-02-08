package pacificsoft.pscomandera;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Adapter.AdapterCuenta;
import pacificsoft.pscomandera.Adapter.AdapterMesa;
import pacificsoft.pscomandera.Enum.StaticVariables;
import pacificsoft.pscomandera.Enum.EnumTipoPeticionAsyncTask;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.Loading.LoadingTask;
import pacificsoft.pscomandera.Modelo.Area;
import pacificsoft.pscomandera.Modelo.Caja;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.Cuenta;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.Mesa;
import pacificsoft.pscomandera.Modelo.MesaCuenta;
import pacificsoft.pscomandera.Modelo.TipoModal;
import pacificsoft.pscomandera.Modelo.Usuario;
import pacificsoft.pscomandera.Util.CargarConfiguracion;
import pacificsoft.pscomandera.Util.ComandaAsync;
import pacificsoft.pscomandera.Util.CustomAlert;


public class MesaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AdapterMesa.ItemMesaListener, CargarConfiguracion.CargaConfiguracionListener, LoadingTask.LoadingTaskListener {

    Area areaSeleccionada;
    List<Mesa> lstMesas;
    RecyclerView reciclerViewMesas;

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    Menu menuNavigationView;

    TextInputEditText textInputEditTextNumeroCuenta;
    private SwipeRefreshLayout swipeRefreshLayout;

    GridLayoutManager GLayoutManager;
    TextView textViewTituloArea;
    AdapterMesa adapterMesa;
    SharedPreferences mPreferences;
    SharedPreferences.Editor editor;

    WebService webService;
    LoadingTask loadingTask;

    Configuracion configuracion;
    Mesa mesaSeleccionada = null;
    TextView textViewTituloActividad;

    Cuenta cuentaSeleccionada = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesa);

        toolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textViewTituloActividad = findViewById(R.id.textViewTituloActividad);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        textViewTituloActividad.setText(getString(R.string.title_activity_mesas));
        setSupportActionBar(toolbar);

        configureNavigationDrawer();

        configureToolbar();

        setUsuarioLogueado();

        textViewTituloArea = findViewById(R.id.textViewTituloArea);

//        usuario = Usuario.class.cast(getIntent().getExtras().getSerializable(getString(R.string.user_data_extra_data_intent)));

        if(lstMesas ==null || lstMesas.size()==0){
            //Show message empty data
        }else{
            //Show recyclerview
        }

        GLayoutManager = new GridLayoutManager(MesaActivity.this,getResources().getInteger(R.integer.columnsGridViewMesas));

        reciclerViewMesas = findViewById(R.id.reciclerViewMesas);
        reciclerViewMesas.setHasFixedSize(true);
        reciclerViewMesas.setLayoutManager(GLayoutManager);


        mPreferences = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE);
        editor = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE).edit();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMesasArea();
            }
        });

        try {
            String jsonAreaSeleccionada = mPreferences.getString(getString(R.string.SHPTAGAreaSeleccioanda),"");

            Gson gson = new Gson();

            Type typeArea = new TypeToken<Area>(){}.getType();

            areaSeleccionada = gson.fromJson(jsonAreaSeleccionada,typeArea);

        }catch (Exception ex){
            Toast.makeText(MesaActivity.this,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }



//        overridePendingTransition(R.anim.slide_in_right, An);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.areas_mesas_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterMesa.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onPostResume() {

        CargarConfiguracion cargarConfiguracion = new CargarConfiguracion(this,MesaActivity.this);
        cargarConfiguracion.execute();
        super.onPostResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id){
            case R.id.nav_actual_activity:
                break;
            case R.id.nav_acerca_de:
                llamarActividad(StaticVariables.REQUEST.ACERCA_DE_ACTIVITY);
                break;
            case R.id.cerrar_sesion:
                alertCerrarSesion();
                break;
        }


        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void cargaConfiguracionCompleta(DatosRegresados datosregresados) {
        try{
            if(!datosregresados.getError()){
                configuracion = Configuracion.class.cast(datosregresados.getDatosRegresados());
                if(configuracion!=null && configuracion.getEndPointServicioPrincipal()!=null && !configuracion.getEndPointServicioPrincipal().equals("")){
                    webService = new WebService(configuracion.getEndPointServicioPrincipal());
                    refreshMesasArea();
//                    fillMesas();
                }
            }else{
                throw  new Exception(Exception.class.cast(datosregresados.getDatosRegresados()));
            }
        }catch (Exception ex){
            Toast.makeText(MesaActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemMesaClick(Mesa mesa) {
        if(mesa!=null){
           tomarMesa(mesa);
        }else {
            mesaSeleccionada = mesa;
        }
    }

    @Override
    public void onLoadedDataLoadingTask(EnumTipoPeticionAsyncTask peticion, DatosRegresados datosregresados) {
        swipeRefreshLayout.setRefreshing(false);
        if(!datosregresados.getError()){
            switch (peticion){
                case CERRAR_CAJA:
                    Toast.makeText(MesaActivity.this,"Error Comandas",Toast.LENGTH_LONG).show();

                    ComandaAsync comandaAsync = new ComandaAsync(MesaActivity.this,StaticVariables.PETICION_COMANDA_ASYNC.LIMPIAR_COMANDAS,configuracion,null,null);
                    comandaAsync.execute();
                    cerrarSesion();
                    break;
                case TOMAR_MESA:

                    if(mesaSeleccionada.getNumeroCuentas()>1){

                        MesaCuenta mesaCuenta = crearListaCuentas();

                        CuentaSeleccionadaDialog(mesaCuenta);
                    }else{
                        Cuenta cuenta = new Cuenta();
                        cuenta.setCuenta("1");

                        cuentaSeleccionada = cuenta;
                        guardarMesaSeleccioanda();
                    }

//                    String idMesa = mesaSeleccionada.getCodigo();
//
//                    loadingTask = new LoadingTask(webService,MesaActivity.this,EnumTipoPeticionAsyncTask.OBTENER_CUENTAS_MESA,getString(R.string.msj_obteniendo_cuentas),idMesa,this,true);
//                    loadingTask.execute();

                    break;
                case AREASYMESAS:
                    fillAreasResult(datosregresados);
                    break;
//                case OBTENER_CUENTAS_MESA:
//                    MesaCuenta mesaCuenta = MesaCuenta.class.cast(datosregresados.getDatosRegresados());
//
//                    if(mesaCuenta.getCuentas()!=null && mesaCuenta.getCuentas().size()>0){
//                        if(mesaCuenta.getCuentas().size()>1){
//                            CuentaSeleccionadaDialog(mesaCuenta);
//                        }else{
//                            cuentaSeleccionada = mesaCuenta.getCuentas().get(0);
//                            guardarMesaSeleccioanda();
//                        }
//                    }else{
//
//                        Cuenta cuenta = new Cuenta();
//                        cuenta.setCuenta("0");
//
//                        cuentaSeleccionada = cuenta;
//                        guardarMesaSeleccioanda();
//
//                    }
//                    break;
            }
        }else{
            Toast.makeText(MesaActivity.this,Exception.class.cast(datosregresados.getDatosRegresados()).getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case StaticVariables.REQUEST.CARTA_ACTIVITY:{
                switch (resultCode){
                    case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION:case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION_PARENT:
                        finalizarActividad(resultCode);
                        break;
                }
                break;
            }
        }

        setSelectedItemNavigationView(R.id.nav_actual_activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void refreshMesasArea(){
        if(configuracion!=null && configuracion.getEndPointServicioPrincipal()!=null && !configuracion.getEndPointServicioPrincipal().equals("")){
            webService = new WebService(configuracion.getEndPointServicioPrincipal());
            loadingTask = new LoadingTask(webService,MesaActivity.this, EnumTipoPeticionAsyncTask.AREASYMESAS,getString(R.string.msj_obteniendo_areas),null,this,true);
            loadingTask.execute();
        }
    }

    public void fillAreasResult(DatosRegresados datosRegresados){

        try{
            if(!datosRegresados.getError()){

                Type listType = new TypeToken<ArrayList<Area>>(){}.getType();

                List<Area> lstAreas = new Gson().fromJson(datosRegresados.getDatosRegresadosString(),listType);

                areaSeleccionada = lstAreas.get(lstAreas.indexOf(areaSeleccionada));

                fillMesas();

//                adapterArea = new AdapterArea(lstAreas,MesaActivity.this,this);
//                reciclerViewAreas.setAdapter(adapterArea);

            }else{
                throw new Exception(Exception.class.cast(Exception.class.cast(datosRegresados.getDatosRegresados())));
            }
        }catch (Exception ex){
            Toast.makeText(MesaActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void guardarMesaSeleccioanda(){
        String mesaSeleccionadaObjetostring = new Gson().toJson(mesaSeleccionada);
        String cuentaSeleccionadaObjetoString = new Gson().toJson(cuentaSeleccionada);
        editor.putString(getString(R.string.SHPTAGMesaSeleccionada),mesaSeleccionadaObjetostring);
        editor.putString(getString(R.string.SHPTAGCuentaSeleccionada),cuentaSeleccionadaObjetoString);
        editor.commit();

        llamarActividad(StaticVariables.REQUEST.CARTA_ACTIVITY);
    }

    private void configureToolbar() {

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

    }


    private void configureNavigationDrawer(){

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        toggle.syncState();

        menuNavigationView = navigationView.getMenu();

        MenuItem nav_actual_activity = menuNavigationView.findItem(R.id.nav_actual_activity);
        nav_actual_activity.setTitle(getString(R.string.nav_drawer_aplicacion_actual_mesa));
        nav_actual_activity.setIcon(R.drawable.ic_mesa);

        setSelectedItemNavigationView(R.id.nav_actual_activity);

        navigationView.setNavigationItemSelectedListener(this);

    }

    private  void setUsuarioLogueado(){
        /**/
        SharedPreferences mPreferences;

        mPreferences = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE);

        String stringUsuario = mPreferences.getString(getString(R.string.SHPTAGUser),"");

        Type typeUsuario = new TypeToken<Usuario>(){}.getType();

        Usuario usuario = new Usuario();

        usuario = new Gson().fromJson(stringUsuario,typeUsuario);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textViewUserName);

        navUsername.setText(usuario.getNombre());

        /**/
    }

    private void setSelectedItemNavigationView(int id){
        MenuItem nav_actual_activity = menuNavigationView.findItem(id);
        nav_actual_activity.setChecked(true);
    }

    private void tomarMesa(Mesa mesa){
        mesaSeleccionada = mesa;
        if(mesaSeleccionada.getEstado() == 0){
            try{

                final CustomAlert alert = new CustomAlert(MesaActivity.this, TipoModal.TOMAR_MESA,null);

                alert.setAceptarClickListener(new CustomAlert.AceptarListener() {
                    @Override
                    public void OnClickAceptar(TipoModal tipoModal) {
                        alert.dismiss();
                        CantidadCuentasDialog();
//                        tomarMesaLlamadoAsync();
                    }
                },"");

                alert.setCancelarClickListener(new CustomAlert.CancelarListener() {
                    @Override
                    public void OnClickCancelar(TipoModal tipoModal) {
                        adapterMesa.mesaSeleccionada = false;
                        alert.dismiss();
                    }
                },"");

                alert.show();
            }
            catch (Exception Ex){
                Toast.makeText(MesaActivity.this,Ex.getMessage().toString(),Toast.LENGTH_LONG).show();
            }



        }else{

//            String idMesa = mesaSeleccionada.getCodigo();
             try {


                 if (mesaSeleccionada.getNumeroCuentas() > 1) {

                     MesaCuenta mesaCuenta = crearListaCuentas();

                     CuentaSeleccionadaDialog(mesaCuenta);
                 } else {
                     Cuenta cuenta = new Cuenta();
                     cuenta.setCuenta("1");

                     cuentaSeleccionada = cuenta;
                     guardarMesaSeleccioanda();
                 }

//            if(mesaCuenta.getCuentas()!=null && mesaCuenta.getCuentas().size()>0){
//                if(mesaCuenta.getCuentas().size()>1){
//                    CuentaSeleccionadaDialog(mesaCuenta);
//                }else{
//                    cuentaSeleccionada = mesaCuenta.getCuentas().get(0);
//                    guardarMesaSeleccioanda();
//                }
//            }else{
//
//                Cuenta cuenta = new Cuenta();
//                cuenta.setCuenta("0");
//
//                cuentaSeleccionada = cuenta;
//                guardarMesaSeleccioanda();
//
//            }
//
//            loadingTask = new LoadingTask(webService,MesaActivity.this,EnumTipoPeticionAsyncTask.OBTENER_CUENTAS_MESA,getString(R.string.msj_obteniendo_cuentas),idMesa,this,true);
//            loadingTask.execute();
             }
             catch (Exception Ex){
                 Toast.makeText(MesaActivity.this,Ex.getMessage().toString(),Toast.LENGTH_LONG).show();
             }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterMesa.mesaSeleccionada = false;
        if(loadingTask!=null){
            loadingTask.cancel(true);
        }

    }


    public void fillMesas(){

        try{
            if(areaSeleccionada!=null){
                textViewTituloArea.setText(areaSeleccionada.getArea());

                lstMesas = areaSeleccionada.getMesas();
                adapterMesa = new AdapterMesa(lstMesas,MesaActivity.this,this);
                reciclerViewMesas.setAdapter(adapterMesa);

            }else{
                throw new Exception(getString(R.string.msj_no_contiene_mesas));
            }
        }catch (Exception ex){
            Toast.makeText(MesaActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }



    private MesaCuenta crearListaCuentas(){
        MesaCuenta mesaCuenta = new MesaCuenta();
        mesaCuenta.setMesa(mesaSeleccionada.getCodigo());

        List<Cuenta> lstCuentas = new ArrayList<>();

        for(int i =1;i<=mesaSeleccionada.getNumeroCuentas();i++){
            Cuenta cuenta = new Cuenta(String.valueOf(i));
            lstCuentas.add(cuenta);
        }

        mesaCuenta.setCuentas(lstCuentas);

        return  mesaCuenta;
    }

    public void tomarMesaLlamadoAsync(){
        loadingTask = new LoadingTask(webService,MesaActivity.this,EnumTipoPeticionAsyncTask.TOMAR_MESA,getString(R.string.msj_tomando_mesa),mesaSeleccionada,this,true);
        loadingTask.execute();
    }

    private void llamarActividad(int requestActivity){

        Intent i ;
        int Parent = StaticVariables.REQUEST.MESA_ACTIVITY;
        switch (requestActivity){

            case StaticVariables.REQUEST.CARTA_ACTIVITY:
                i = new Intent(MesaActivity.this,CartaActivity.class);
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                startActivityForResult(i,requestActivity);
                break;
            case StaticVariables.REQUEST.ACERCA_DE_ACTIVITY:
                i = new Intent(MesaActivity.this,AcercaDeActivity.class);
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                startActivityForResult(i,requestActivity);
        }
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }


    private void cerrarSesion(){
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE).edit();
        editor.putString(getString(R.string.SHPTAGUser),"");
        editor.commit();
        finalizarActividad(StaticVariables.RESULT_ACTIVITY.CERRAR_SESION);
    }

    private void finalizarActividad(int resultActivity){
        Intent i;
        switch (resultActivity){
            case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION:case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION_PARENT:{
                if(resultActivity == StaticVariables.RESULT_ACTIVITY.CERRAR_SESION){
                    i = new Intent(MesaActivity.this,LoginActivity.class);
                    i.putExtra(getString(R.string.dato_extra_intent_request), StaticVariables.REQUEST.OTHER_ACTIVITY);
                    startActivity(i);
                    resultActivity = StaticVariables.RESULT_ACTIVITY.CERRAR_SESION_PARENT;
                }

//                i = new Intent();
//                i.putExtra(getString(R.string.dato_extra_intent_tipo_request),RequestAndResultActivity.CERRAR_SESION);
                setResult(resultActivity);
                finish();
            }
        }
    }


    public void CantidadCuentasDialog() {
        try {


            final Button btnAceptarPopup, btnCancelarPopup;
            final MaterialRippleLayout rippleCancelar;
            TextView lblTituloAlertCustom;

            final Dialog dialogNumeroCuentas = new Dialog(MesaActivity.this);
            dialogNumeroCuentas.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialogNumeroCuentas.setContentView(R.layout.content_tomar_mesa);

            lblTituloAlertCustom = (TextView) dialogNumeroCuentas.findViewById(R.id.lblTituloAlertCustom);
            textInputEditTextNumeroCuenta = dialogNumeroCuentas.findViewById(R.id.textInputEditTextNumeroCuenta);

            //Obtiene el boton de Aceptar del popup
            btnAceptarPopup = (Button) dialogNumeroCuentas.findViewById(R.id.btnAceptarPopup);

            //Obtiene el boton de Cancelar del popup
            btnCancelarPopup = (Button) dialogNumeroCuentas.findViewById(R.id.btnCancelarPopup);
            rippleCancelar = dialogNumeroCuentas.findViewById(R.id.rippleCancelar);

            lblTituloAlertCustom.setText(mesaSeleccionada.getDescripcion());

            btnAceptarPopup.setEnabled(false);


            textInputEditTextNumeroCuenta.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("") || Integer.valueOf(s.toString()) == 0) {
                        btnAceptarPopup.setEnabled(false);
                    } else {
                        btnAceptarPopup.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            btnAceptarPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mesaSeleccionada.setNumeroCuentas(Integer.valueOf(!textInputEditTextNumeroCuenta.getText().toString().equals("") ? textInputEditTextNumeroCuenta.getText().toString() : "1"));
                    tomarMesaLlamadoAsync();
                    dialogNumeroCuentas.dismiss();
                }
            });

            btnCancelarPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogNumeroCuentas.cancel();
                }
            });

            rippleCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogNumeroCuentas.cancel();
                }
            });

            dialogNumeroCuentas.setCancelable(true);

            dialogNumeroCuentas.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    adapterMesa.mesaSeleccionada = false;
                }
            });

            // dialogNumeroCuentas.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialogNumeroCuentas.show();
        }
        catch (Exception Ex){
            Toast.makeText(MesaActivity.this,Ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void CuentaSeleccionadaDialog(MesaCuenta mesaCuenta) {

        final Button btnAceptarPopup, btnCancelarPopup;
        final MaterialRippleLayout rippleCancelar;
        TextView lblTituloAlertCustom;

        final Dialog dialogCuentaSeleccionada = new Dialog(MesaActivity.this);
        dialogCuentaSeleccionada.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogCuentaSeleccionada.setContentView(R.layout.content_seleccionar_cuenta);

        final RecyclerView recyclerViewSeleccionarCuenta = dialogCuentaSeleccionada.findViewById(R.id.reciclerViewSeleccionarCuetna);

        //Obtiene el boton de Aceptar del popup
        btnAceptarPopup = (Button) dialogCuentaSeleccionada.findViewById(R.id.btnAceptarPopup);

        lblTituloAlertCustom = (TextView) dialogCuentaSeleccionada.findViewById(R.id.lblTituloAlertCustom);

        //Obtiene el boton de Cancelar del popup
        btnCancelarPopup = (Button) dialogCuentaSeleccionada.findViewById(R.id.btnCancelarPopup);
        rippleCancelar = dialogCuentaSeleccionada.findViewById(R.id.rippleCancelar);

        lblTituloAlertCustom.setText(getString(R.string.title_dialog_mesa)+" "+mesaCuenta.getMesa());

        final AdapterCuenta adapterCuenta = new AdapterCuenta(mesaCuenta.getCuentas(),MesaActivity.this);

        GridLayoutManager GLayoutManagerCuenta = new GridLayoutManager(MesaActivity.this,1);
        recyclerViewSeleccionarCuenta.setHasFixedSize(true);
        recyclerViewSeleccionarCuenta.setLayoutManager(GLayoutManagerCuenta);

        recyclerViewSeleccionarCuenta.setAdapter(adapterCuenta);


        btnAceptarPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuentaSeleccionada = adapterCuenta.getSelectedItem();
                guardarMesaSeleccioanda();
                dialogCuentaSeleccionada.dismiss();

            }
        });

        btnCancelarPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCuentaSeleccionada.cancel();
            }
        });

        rippleCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCuentaSeleccionada.cancel();
            }
        });

        dialogCuentaSeleccionada.setCancelable(true);

        dialogCuentaSeleccionada.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                adapterMesa.mesaSeleccionada = false;
                refreshMesasArea();
            }
        });

        dialogCuentaSeleccionada.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogCuentaSeleccionada.show();

    }

    private void alertCerrarSesion(){
        final CustomAlert alert = new CustomAlert(MesaActivity.this,TipoModal.CERRAR_SESION,null);
        alert.setAceptarClickListener(new CustomAlert.AceptarListener() {
            @Override
            public void OnClickAceptar(TipoModal tipoModal) {
                alert.dismiss();
                cerrarSesionService();
            }
        },"");

        alert.setCancelarClickListener(new CustomAlert.CancelarListener() {
            @Override
            public void OnClickCancelar(TipoModal tipoModal) {
                setSelectedItemNavigationView(R.id.nav_actual_activity);
                alert.dismiss();
            }
        },"");

        alert.show();
    }

    private void cerrarSesionService(){
        Caja caja = new Caja();
        caja.setCodigo(configuracion.getCaja());

        Toast.makeText(MesaActivity.this,"CERRANDO SESION".toString(),Toast.LENGTH_LONG).show();

        loadingTask = new LoadingTask(webService,MesaActivity.this,EnumTipoPeticionAsyncTask.CERRAR_CAJA,getString(R.string.msj_cerrando_sesion),caja,this,true);
        loadingTask.execute();
    }

}
