package pacificsoft.pscomandera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import pacificsoft.pscomandera.Adapter.AdapterCarta;
import pacificsoft.pscomandera.Enum.EnumTipoPeticionAsyncTask;
import pacificsoft.pscomandera.Enum.StaticVariables;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.Loading.LoadingTask;
import pacificsoft.pscomandera.Modelo.Area;
import pacificsoft.pscomandera.Modelo.Caja;
import pacificsoft.pscomandera.Modelo.Carta;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.Cuenta;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.Mesa;
import pacificsoft.pscomandera.Modelo.TipoModal;
import pacificsoft.pscomandera.Modelo.Usuario;
import pacificsoft.pscomandera.Util.CargarConfiguracion;
import pacificsoft.pscomandera.Util.ComandaAsync;
import pacificsoft.pscomandera.Util.CustomAlert;

public class CartaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , CargarConfiguracion.CargaConfiguracionListener,LoadingTask.LoadingTaskListener, AdapterCarta.ItemCartaListener {

    Configuracion configuracion;
    List<Carta> lstCartas;
    GridLayoutManager GLayoutManager;
    RecyclerView recyclerViewCartas;
    AdapterCarta adapterCarta;
    SharedPreferences.Editor editor;
    SwipeRefreshLayout swipeRefreshLayout;

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    Menu menuNavigationView;

    WebService webService;
    LoadingTask loadingTask;
    TextView textViewTituloActividad,textViewMesaSeleccionada,textViewAreaSeleccionada,textViewSubTituloActividad;

    Area areaSeleccionada;
    Mesa mesaSeleccionada ;
    Cuenta cuentaSeleccionada;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        textViewTituloActividad = findViewById(R.id.textViewTituloActividad);
        textViewSubTituloActividad = findViewById(R.id.textViewSubTituloActividad);
        textViewMesaSeleccionada = findViewById(R.id.textViewMesaSeleccionada);
        textViewAreaSeleccionada = findViewById(R.id.textViewAreaSeleccionada);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        textViewTituloActividad.setText(getString(R.string.title_activity_carta));

        mPreferences = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE);
        String stringMesaSeleccionada = mPreferences.getString(getString(R.string.SHPTAGMesaSeleccionada),"");
        String jsonAreaSeleccionada = mPreferences.getString(getString(R.string.SHPTAGAreaSeleccioanda),"");
        String jsonCuentaSeleccionada = mPreferences.getString(getString(R.string.SHPTAGCuentaSeleccionada),"");

        Type typeMesaSeleccionada = new TypeToken<Mesa>(){}.getType();
        mesaSeleccionada = new Gson().fromJson(stringMesaSeleccionada,typeMesaSeleccionada);

        Type typeArea = new TypeToken<Area>(){}.getType();
        areaSeleccionada = new Gson().fromJson(jsonAreaSeleccionada,typeArea);

        Type typeCuenta = new TypeToken<Cuenta>(){}.getType();
        cuentaSeleccionada = new Gson().fromJson(jsonCuentaSeleccionada,typeCuenta);

        textViewAreaSeleccionada.setText(areaSeleccionada.getArea());
        textViewMesaSeleccionada.setText(mesaSeleccionada.getDescripcion());
        textViewSubTituloActividad.setText(getString(R.string.nav_drawer_cuenta)+" "+cuentaSeleccionada.getCuenta());

        mesaSeleccionada = null;
        areaSeleccionada = null;

        setSupportActionBar(toolbar);

        configureNavigationDrawer();

        configureToolbar();

        setUsuarioLogueado();

        if(lstCartas ==null || lstCartas.size()==0){
            //Show message empty data
        }else{
            //Show recyclerview
        }

        GLayoutManager = new GridLayoutManager(CartaActivity.this,getResources().getInteger(R.integer.columnsGridViewCartas));

        recyclerViewCartas = findViewById(R.id.recyclerViewCartas);
        recyclerViewCartas.setHasFixedSize(true);
        recyclerViewCartas.setLayoutManager(GLayoutManager);

        editor = getSharedPreferences(getString(R.string.SHPName),MODE_PRIVATE).edit();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillcartas();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menuNavigationView; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cartas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_carrito) {
            llamarActividad(StaticVariables.REQUEST.COMANDA_ACTIVITY);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

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
    protected void onPostResume() {
        CargarConfiguracion cargarConfiguracion = new CargarConfiguracion(this,CartaActivity.this);
        cargarConfiguracion.execute();
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(loadingTask!=null && !loadingTask.isCancelled()){
            loadingTask.cancel(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case StaticVariables.REQUEST.COMANDA_ACTIVITY:
                switch (resultCode){
                    case StaticVariables.RESULT_ACTIVITY.COMANDA_ENVIADA:
                        setResult(resultCode);
                        finish();
                        break;
                }
                break;
            case StaticVariables.REQUEST.GRUPO_ACTIVITY:{

                finalizarActividad(resultCode);

                //switch (resultCode){
                //    case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION:case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION_PARENT:
                //        finalizarActividad(resultCode);
                //        break;
                //    case StaticVariables.RESULT_ACTIVITY.COMANDA_ENVIADA:
                //        finalizarActividad(resultCode);
                //        break;
                //}
                break;
            }
        }
        setSelectedItemNavigationView(R.id.nav_actual_activity);
    }

    /*********************** INTERFACES**********************/

    @Override
    public void cargaConfiguracionCompleta(DatosRegresados datosregresados) {

        if(!datosregresados.getError()){
            configuracion = Configuracion.class.cast(datosregresados.getDatosRegresados());

            fillcartas();

        }else{
            Toast.makeText(CartaActivity.this,Exception.class.cast(datosregresados.getDatosRegresados()).getMessage(),Toast.LENGTH_LONG).show();
        }

        this.configuracion = configuracion;
    }



    @Override
    public void onLoadedDataLoadingTask(EnumTipoPeticionAsyncTask peticion, DatosRegresados datosregresados) {

        swipeRefreshLayout.setRefreshing(false);

        if(!datosregresados.getError()){
            switch (peticion){
                case CARTAS:
                    lstCartas = (List<Carta>)datosregresados.getLstDatosRegresados();
                    adapterCarta = new AdapterCarta(lstCartas,CartaActivity.this,this);
                    recyclerViewCartas.setAdapter(adapterCarta);
                    break;
                case CERRAR_CAJA:
                    ComandaAsync comandaAsync = new ComandaAsync(CartaActivity.this,StaticVariables.PETICION_COMANDA_ASYNC.LIMPIAR_COMANDAS,configuracion,null,null);
                    comandaAsync.execute();
                    cerrarSesion();
                    break;
            }
        }else{
            Toast.makeText(CartaActivity.this,Exception.class.cast(datosregresados.getDatosRegresados()).getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onItemCartaClick(Carta carta) {

        String cartaSeleccionada = new Gson().toJson(carta);
        editor.putString(getString(R.string.SHPTAGCartaSeleccionada),cartaSeleccionada);
        editor.commit();

        llamarActividad(StaticVariables.REQUEST.GRUPO_ACTIVITY);

    }


    /*********************** INTERFACES**********************/

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
        nav_actual_activity.setTitle(getString(R.string.nav_drawer_aplicacion_actual_carta));
        nav_actual_activity.setIcon(R.drawable.ic_carta);

        setSelectedItemNavigationView(R.id.nav_actual_activity);


        navigationView.setNavigationItemSelectedListener(this);

    }

    private void fillcartas(){
        if(configuracion!=null && configuracion.getEndPointServicioPrincipal()!=null && !configuracion.getEndPointServicioPrincipal().equals("")){
            webService = new WebService(configuracion.getEndPointServicioPrincipal());

            loadingTask = new LoadingTask(webService,CartaActivity.this, EnumTipoPeticionAsyncTask.CARTAS,getString(R.string.msj_obteniendo_cartas),null,this,true);
            loadingTask.execute();
        }else{
            Toast.makeText(CartaActivity.this,"Asegurese que la configuracion este correcta",Toast.LENGTH_LONG).show();
        }
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

    private void llamarActividad(int requestActivity){

        Intent i ;
        int Parent = StaticVariables.REQUEST.CARTA_ACTIVITY;
        switch (requestActivity){
            case StaticVariables.REQUEST.COMANDA_ACTIVITY:
                i = new Intent(CartaActivity.this,ComandaActivity.class);
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                startActivityForResult(i,requestActivity);
                break;
            case StaticVariables.REQUEST.GRUPO_ACTIVITY:
                i = new Intent(CartaActivity.this,GrupoCartaActivity.class);
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                startActivityForResult(i,requestActivity);
                break;
            case StaticVariables.REQUEST.ACERCA_DE_ACTIVITY:
                i = new Intent(CartaActivity.this,AcercaDeActivity.class);
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                startActivityForResult(i,requestActivity);
                break;
        }
        if(StaticVariables.REQUEST.COMANDA_ACTIVITY != requestActivity){
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
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
                    i = new Intent(CartaActivity.this,LoginActivity.class);
                    i.putExtra(getString(R.string.dato_extra_intent_request), StaticVariables.REQUEST.OTHER_ACTIVITY);
                    startActivity(i);
                    resultActivity = StaticVariables.RESULT_ACTIVITY.CERRAR_SESION_PARENT;
                }
                setResult(resultActivity);
                finish();

            }
            case StaticVariables.RESULT_ACTIVITY.COMANDA_ENVIADA:
                setResult(resultActivity);
                finish();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(loadingTask!=null){
            loadingTask.cancel(true);
        }
    }

    private void alertCerrarSesion(){
        final CustomAlert alert = new CustomAlert(CartaActivity.this, TipoModal.CERRAR_SESION,null);
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

        loadingTask = new LoadingTask(webService,CartaActivity.this,EnumTipoPeticionAsyncTask.CERRAR_CAJA,getString(R.string.msj_cerrando_sesion),caja,this,true);
        loadingTask.execute();
    }
}
