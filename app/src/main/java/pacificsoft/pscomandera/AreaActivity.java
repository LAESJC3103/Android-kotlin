package pacificsoft.pscomandera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pacificsoft.pscomandera.Adapter.AdapterArea;
import pacificsoft.pscomandera.Enum.StaticVariables;
import pacificsoft.pscomandera.Enum.EnumTipoPeticionAsyncTask;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.Loading.LoadingTask;
import pacificsoft.pscomandera.Modelo.Area;
import pacificsoft.pscomandera.Modelo.Caja;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.TipoModal;
import pacificsoft.pscomandera.Modelo.Usuario;
import pacificsoft.pscomandera.Util.CargarConfiguracion;
import pacificsoft.pscomandera.Util.ComandaAsync;
import pacificsoft.pscomandera.Util.CustomAlert;

public class AreaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AdapterArea.ItemAreaListener, CargarConfiguracion.CargaConfiguracionListener,LoadingTask.LoadingTaskListener {

    List<Area> lstAreas;
    Configuracion configuracion;
    RecyclerView reciclerViewAreas ;
    private SwipeRefreshLayout swipeRefreshLayout;

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    Menu menuNavigationView;

    GridLayoutManager GLayoutManager;
    AdapterArea adapterArea;
    WebService webService;

    LoadingTask loadingTask;

    TextView textViewTituloActividad;

    private boolean loadResume = true;

    int result = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        toolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        textViewTituloActividad = findViewById(R.id.textViewTituloActividad);

        textViewTituloActividad.setText(getString(R.string.title_activity_areas));

        setSupportActionBar(toolbar);

        configureNavigationDrawer();

        configureToolbar();

        setUsuarioLogueado();

        if(lstAreas ==null || lstAreas.size()==0){
            //Show message empty data
        }else{
            //Show recyclerview
        }

        GLayoutManager = new GridLayoutManager(AreaActivity.this,getResources().getInteger(R.integer.columnsGridViewAreas));

        reciclerViewAreas = findViewById(R.id.reciclerViewAreas);
        reciclerViewAreas.setHasFixedSize(true);
        reciclerViewAreas.setLayoutManager(GLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillAreas();
            }
        });

//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menuNavigationView) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.areas_mesas_menu,menuNavigationView);

        MenuItem searchItem = menuNavigationView.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterArea.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(loadResume){
            loadResume = false;
            CargarConfiguracion cargarConfiguracion = new CargarConfiguracion(this,AreaActivity.this);
            cargarConfiguracion.execute();
        }
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{

            alertCerrarSesion();

//            super.onBackPressed();
        }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case StaticVariables.REQUEST.MESA_ACTIVITY:{
                switch (resultCode){
                    case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION:case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION_PARENT:
                        finish();
                        break;
                }
                break;
            }
        }

        setSelectedItemNavigationView(R.id.nav_actual_activity);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterArea.areaSeleccionada = false;
        if(loadingTask!=null){
            loadingTask.cancel(true);
        }
    }

    @Override
    public void onLoadedDataLoadingTask(EnumTipoPeticionAsyncTask peticion, DatosRegresados datosregresados) {
        swipeRefreshLayout.setRefreshing(false);
        switch (peticion){
            case AREASYMESAS:
                fillAreasResult(datosregresados);
                break;
            case CERRAR_CAJA:
                if(!datosregresados.getError()){
                    ComandaAsync comandaAsync = new ComandaAsync(AreaActivity.this,StaticVariables.PETICION_COMANDA_ASYNC.LIMPIAR_COMANDAS,configuracion,null,null);
                    comandaAsync.execute();
                    cerrarSesion();
                }else{
                    Toast.makeText(AreaActivity.this,Exception.class.cast(datosregresados.getDatosRegresados()).getMessage(),Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onItemAreaClick(Area area) {
        if(area!=null){
            Gson gson = new Gson();
            String areaSeleccionadaObjetostring = gson.toJson(area);

            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE).edit();
            editor.putString(getString(R.string.SHPTAGAreaSeleccioanda),areaSeleccionadaObjetostring);
            editor.commit();

            llamarActividad(StaticVariables.REQUEST.MESA_ACTIVITY);

        }
    }

    @Override
    public void cargaConfiguracionCompleta(DatosRegresados datosRegresados) {
        try{
            if(!datosRegresados.getError()){
                configuracion = Configuracion.class.cast(datosRegresados.getDatosRegresados());

                fillAreas();

            }else{
                throw  new Exception(Exception.class.cast(datosRegresados.getDatosRegresados()));
            }
        }catch (Exception ex){
            Toast.makeText(AreaActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void fillAreas(){
        if(configuracion!=null && configuracion.getEndPointServicioPrincipal()!=null && !configuracion.getEndPointServicioPrincipal().equals("")){
            webService = new WebService(configuracion.getEndPointServicioPrincipal());
            loadingTask = new LoadingTask(webService,AreaActivity.this, EnumTipoPeticionAsyncTask.AREASYMESAS,getString(R.string.msj_obteniendo_areas),null,this,true);
            loadingTask.execute();
        }
    }

    private void alertCerrarSesion(){
        final CustomAlert alert = new CustomAlert(AreaActivity.this,TipoModal.CERRAR_SESION,null);
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

        loadingTask = new LoadingTask(webService,AreaActivity.this,EnumTipoPeticionAsyncTask.CERRAR_CAJA,getString(R.string.msj_cerrando_sesion),caja,this,true);
        loadingTask.execute();
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
        nav_actual_activity.setTitle(getString(R.string.nav_drawer_aplicacion_actual_area));
        nav_actual_activity.setIcon(R.drawable.ic_area);

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

    public void fillAreasResult(DatosRegresados datosRegresados){

        try{
            if(!datosRegresados.getError()){

                Type listType = new TypeToken<ArrayList<Area>>(){}.getType();

                lstAreas = new Gson().fromJson(datosRegresados.getDatosRegresadosString(),listType);
                adapterArea = new AdapterArea(lstAreas,AreaActivity.this,this);
                reciclerViewAreas.setAdapter(adapterArea);

            }else{
                throw new Exception(Exception.class.cast(Exception.class.cast(datosRegresados.getDatosRegresados())));
            }
        }catch (Exception ex){
            Toast.makeText(AreaActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }


    private void llamarActividad(int requestActivity){
        Intent i;
        int Parent = StaticVariables.REQUEST.MESA_ACTIVITY;

        switch (requestActivity){
            case StaticVariables.REQUEST.ACERCA_DE_ACTIVITY:
                i = new Intent(AreaActivity.this,AcercaDeActivity.class);
                //Dato extra para saber quien hace el llamado
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                //Se envia en el request la actividad que s esta llamando
                startActivityForResult(i, requestActivity);
                break;
            case StaticVariables.REQUEST.MESA_ACTIVITY:
                i = new Intent(AreaActivity.this,MesaActivity.class);
                //Dato extra para saber quien hace el llamado
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                //Se envia en el request la actividad que s esta llamando
                startActivityForResult(i, requestActivity);
                break;
            case StaticVariables.REQUEST.COMANDA_ACTIVITY:
                break;
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
            case StaticVariables.RESULT_ACTIVITY.CERRAR_SESION:{
                i = new Intent(AreaActivity.this,LoginActivity.class);
                i.putExtra(getString(R.string.dato_extra_intent_request), StaticVariables.REQUEST.OTHER_ACTIVITY);
                startActivity(i);
                finish();
            }
        }
    }

}
