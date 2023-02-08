package pacificsoft.pscomandera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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

import pacificsoft.pscomandera.Adapter.ViewPagerAdapterGrupo;
import pacificsoft.pscomandera.Enum.EnumTipoPeticionAsyncTask;
import pacificsoft.pscomandera.Enum.StaticVariables;
import pacificsoft.pscomandera.Fragment.FragmentGrupo;
import pacificsoft.pscomandera.KSOAP2.WebService;
import pacificsoft.pscomandera.Loading.LoadingTask;
import pacificsoft.pscomandera.Modelo.Area;
import pacificsoft.pscomandera.Modelo.Caja;
import pacificsoft.pscomandera.Modelo.Carta;
import pacificsoft.pscomandera.Modelo.ComandaArticulo;
import pacificsoft.pscomandera.Modelo.Configuracion;
import pacificsoft.pscomandera.Modelo.Cuenta;
import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.Mesa;
import pacificsoft.pscomandera.Modelo.Platillo;
import pacificsoft.pscomandera.Modelo.TipoModal;
import pacificsoft.pscomandera.Modelo.Usuario;
import pacificsoft.pscomandera.Util.CargarConfiguracion;
import pacificsoft.pscomandera.Util.ComandaAsync;
import pacificsoft.pscomandera.Util.CustomAlert;
import pacificsoft.pscomandera.Util.CustomSnackbar;

public class GrupoCartaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CargarConfiguracion.CargaConfiguracionListener, LoadingTask.LoadingTaskListener , FragmentGrupo.ItemFragmentListener{



    TabLayout tabLayoutGrupos;
    ViewPager viewPagerGrupos;

    Carta cartaSeleccionada;

    SharedPreferences sharedPreferences;

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    Menu menuNavigationView;

    private CoordinatorLayout coordinator;

    ViewPagerAdapterGrupo viewPagerAdapterGrupo;

    Configuracion configuracion;

    TextView textViewTituloActividad,textViewMesaSeleccionada,textViewAreaSeleccionada,textViewSubTituloActividad;

    Area areaSeleccionada;
    Mesa mesaSeleccionada ;
    Cuenta cuentaSeleccionada;
    SharedPreferences mPreferences;

    boolean platilloSeleccionado = false;

    WebService webService;
    LoadingTask loadingTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_carta);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        tabLayoutGrupos = findViewById(R.id.tabLayoutGrupos);
        viewPagerGrupos = findViewById(R.id.viewPagerGrupos);
        coordinator = findViewById(R.id.coordinator);
        textViewTituloActividad = findViewById(R.id.textViewTituloActividad);
        textViewSubTituloActividad = findViewById(R.id.textViewSubTituloActividad);
        textViewMesaSeleccionada = findViewById(R.id.textViewMesaSeleccionada);
        textViewAreaSeleccionada = findViewById(R.id.textViewAreaSeleccionada);



        setSupportActionBar(toolbar);

        configureNavigationDrawer();

        configureToolbar();

        setUsuarioLogueado();

        sharedPreferences = getSharedPreferences(getString(R.string.SHPName),MODE_PRIVATE);

        tabLayoutGrupos.setupWithViewPager(viewPagerGrupos);

        tabLayoutGrupos.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPagerGrupos.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
            llamarActividad(StaticVariables.REQUEST.COMANDA_ACTIVITY,null);
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
                llamarActividad(StaticVariables.REQUEST.ACERCA_DE_ACTIVITY,null);
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


        mPreferences = getSharedPreferences(getString(R.string.SHPName), MODE_PRIVATE);
        String stringMesaSeleccionada = mPreferences.getString(getString(R.string.SHPTAGMesaSeleccionada),"");
        String jsonAreaSeleccionada = mPreferences.getString(getString(R.string.SHPTAGAreaSeleccioanda),"");
        String jsonCuentaSeleccionada = mPreferences.getString(getString(R.string.SHPTAGCuentaSeleccionada),"");

        Type typeMesaSeleccionada = new TypeToken<Mesa>(){}.getType();
        mesaSeleccionada = new Gson().fromJson(stringMesaSeleccionada,typeMesaSeleccionada);

        Type typeArea = new TypeToken<Area>(){}.getType();
        areaSeleccionada = new Gson().fromJson(jsonAreaSeleccionada,typeArea);

        textViewAreaSeleccionada.setText(areaSeleccionada.getArea());
        textViewMesaSeleccionada.setText(mesaSeleccionada.getDescripcion());

        Type typeCuenta = new TypeToken<Cuenta>(){}.getType();
        cuentaSeleccionada = new Gson().fromJson(jsonCuentaSeleccionada,typeCuenta);

        mesaSeleccionada = null;
        areaSeleccionada = null;

        String tempCartaSeleccionada = sharedPreferences.getString(getString(R.string.SHPTAGCartaSeleccionada),"");



        if(tempCartaSeleccionada!=""){
            Type typeCarta = new TypeToken<Carta>(){}.getType();
            cartaSeleccionada = new Gson().fromJson(tempCartaSeleccionada,typeCarta);
            textViewTituloActividad.setText(cartaSeleccionada.getDescripcion().toUpperCase());
            textViewSubTituloActividad.setText(getString(R.string.nav_drawer_cuenta)+" "+cuentaSeleccionada.getCuenta());

            CargarConfiguracion cargarConfiguracion = new CargarConfiguracion(this,GrupoCartaActivity.this);
            cargarConfiguracion.execute();

        }else{
            finish();
        }


        super.onPostResume();
    }

    @Override
    public void onLoadedDataLoadingTask(EnumTipoPeticionAsyncTask peticion, DatosRegresados datosregresados) {
        if(!datosregresados.getError()){
            switch (peticion){
                case CERRAR_CAJA:
                    ComandaAsync comandaAsync = new ComandaAsync(GrupoCartaActivity.this,StaticVariables.PETICION_COMANDA_ASYNC.LIMPIAR_COMANDAS,configuracion,null,null);
                    comandaAsync.execute();
                    cerrarSesion();
                    break;
            }
        }else{
            Toast.makeText(GrupoCartaActivity.this,Exception.class.cast(datosregresados.getDatosRegresados()).getMessage(),Toast.LENGTH_LONG).show();
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
            case StaticVariables.REQUEST.DETALLE_COMANDA_AGREGAR_ACTIVITY: case StaticVariables.REQUEST.MODIFICADOR_ACTIVITY:

                switch (resultCode){
                    case StaticVariables.RESULT_ACTIVITY.SE_AGREGO_CORRECTAMENTE:

                        CustomSnackbar customSnackbar = CustomSnackbar.make(coordinator, CustomSnackbar.LENGTH_SHORT);
                        customSnackbar.setText(getString(R.string.msj_agregado_correctamente));
                        customSnackbar.setAction(getString(R.string.msj_boton_aceptar), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO: handle click here
                            }
                        });
                        customSnackbar.show();

                        break;
                }

                break;
        }

        setSelectedItemNavigationView(R.id.nav_actual_activity);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.platilloSeleccionado = false;
        viewPagerAdapterGrupo.platilloSeleccionado = false;
    }

    //    @Override
//    protected void onPause() {
//        cartaSeleccionada.setGrupos(new ArrayList<Grupo>());
//        viewPagerAdapterGrupo.notifyDataSetChanged();
//        super.onPause();
//    }

    @Override
    public void cargaConfiguracionCompleta(DatosRegresados datosregresados) {
        if(!datosregresados.getError()){
            configuracion = Configuracion.class.cast(datosregresados.getDatosRegresados());
            webService = new WebService(configuracion.getEndPointServicioPrincipal());

            viewPagerAdapterGrupo = new ViewPagerAdapterGrupo(getSupportFragmentManager(),cartaSeleccionada.getGrupos().size(),cartaSeleccionada.getGrupos());
            viewPagerGrupos.setAdapter(viewPagerAdapterGrupo);

        }else{
            configuracion = null;
            Toast.makeText(GrupoCartaActivity.this,Exception.class.cast(datosregresados.getDatosRegresados()).getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle oldInstanceState) {
        super.onSaveInstanceState(oldInstanceState);
        oldInstanceState.clear();
    }

    @Override
    public void OnClickItemFragmentGrupo(Platillo platilloSeleccionado) {
        if(!this.platilloSeleccionado){
            this.platilloSeleccionado = true;
            viewPagerAdapterGrupo.platilloSeleccionado = true;

            ComandaArticulo comandaArticuloSeleccionado = new ComandaArticulo();

            comandaArticuloSeleccionado.setCAN_PRO(platilloSeleccionado.getCantidad());
            comandaArticuloSeleccionado.setCOD_ART(platilloSeleccionado.getId());
            comandaArticuloSeleccionado.setDES_PRO(platilloSeleccionado.getDescripcion());
            comandaArticuloSeleccionado.setImagen(platilloSeleccionado.getImagen());
            comandaArticuloSeleccionado.setPRE_ART(platilloSeleccionado.getPrecio());
            comandaArticuloSeleccionado.setModificable(platilloSeleccionado.getModificable());

            if(platilloSeleccionado.getModificable()==1){
                Intent i = new Intent(GrupoCartaActivity.this,ModificadoresActivity.class);
                i.putExtra(getString(R.string.dato_extra_articulo_comanda),comandaArticuloSeleccionado);

                llamarActividad(StaticVariables.REQUEST.MODIFICADOR_ACTIVITY,i);
            }else{
                Intent i = new Intent(GrupoCartaActivity.this,DetalleArticuloComandaActivity.class);
                i.putExtra(getString(R.string.dato_extra_articulo_comanda),comandaArticuloSeleccionado);

                llamarActividad(StaticVariables.REQUEST.DETALLE_COMANDA_AGREGAR_ACTIVITY,i);
            }
        }
    }

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
        nav_actual_activity.setTitle(getString(R.string.nav_drawer_aplicacion_actual_grupo));
        nav_actual_activity.setIcon(R.drawable.ic_grupos);

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

    private void llamarActividad(int requestActivity,Intent i){

        int Parent = StaticVariables.REQUEST.GRUPO_ACTIVITY;

        switch (requestActivity){
            case StaticVariables.REQUEST.COMANDA_ACTIVITY:
                i = new Intent(GrupoCartaActivity.this,ComandaActivity.class);
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                startActivityForResult(i,requestActivity);
                break;
            case StaticVariables.REQUEST.ACERCA_DE_ACTIVITY:
                i = new Intent(GrupoCartaActivity.this,AcercaDeActivity.class);
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                startActivityForResult(i,requestActivity);
                break;

            case StaticVariables.REQUEST.DETALLE_COMANDA_AGREGAR_ACTIVITY:
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                startActivityForResult(i,requestActivity);
                break;
            case StaticVariables.REQUEST.MODIFICADOR_ACTIVITY:
                i.putExtra(getString(R.string.dato_extra_intent_request), Parent);
                startActivityForResult(i,requestActivity);
                break;
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
                    i = new Intent(GrupoCartaActivity.this,LoginActivity.class);
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

    private void alertCerrarSesion(){
        final CustomAlert alert = new CustomAlert(GrupoCartaActivity.this, TipoModal.CERRAR_SESION,null);
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

        loadingTask = new LoadingTask(webService,GrupoCartaActivity.this,EnumTipoPeticionAsyncTask.CERRAR_CAJA,getString(R.string.msj_cerrando_sesion),caja,this,true);
        loadingTask.execute();
    }

}